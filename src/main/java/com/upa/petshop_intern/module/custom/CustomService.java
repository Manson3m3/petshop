package com.upa.petshop_intern.module.custom;


import com.upa.petshop_intern.common.BaseService;
import com.upa.petshop_intern.common.exception.ErrorCode;
import com.upa.petshop_intern.common.exception.WebBackendException;
import com.upa.petshop_intern.common.util.ConvertUtil;
import com.upa.petshop_intern.common.util.EncryptionUtil;
import com.upa.petshop_intern.common.util.RowConverter;
import com.upa.petshop_intern.entity.Custom;
import com.upa.petshop_intern.entity.CustomRole;
import com.upa.petshop_intern.entity.Role;

import com.upa.petshop_intern.repository.CustomImageRepository;
import com.upa.petshop_intern.repository.CustomRepository;
import com.upa.petshop_intern.repository.CustomRoleRepository;
import com.upa.petshop_intern.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.yao on 2017/7/18.
 */
@Service
@Transactional
public class CustomService extends BaseService {
    @Autowired
    CustomRepository customRepository;
    @Autowired
    CustomRoleRepository customRoleRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CustomImageRepository customImageRepository;

    /**
     * 获取顾客列表
     */
    public Page<CustomData> findCustoms(Pageable pageable) throws Exception {
        Page<Custom> customPage = customRepository.findAll(pageable);
        //找不到顾客
        if (customPage == null) {
            throw new WebBackendException(ErrorCode.QUERY_DATA_EMPTY);
        }
        return toCustomDataPage(customPage, pageable);
    }

    /**
     * 根据顾客名搜索
     */
    public Page<CustomData> searchByCustomName(String customName, Pageable pageable) throws Exception {
        Page<Custom> customPage = customRepository.findAllByCustomNameLike("%" + customName + "%", pageable);
        //找不到顾客
        if (customPage == null)
            throw new WebBackendException(ErrorCode.ROLE_NOT_FOUND);
        return toCustomDataPage(customPage, pageable);
    }

    /**
     * 获取顾客
     */
    public CustomData findCustomById(Long customId) throws Exception {
        Custom custom = customRepository.findById(customId);
        if (custom == null)
            throw new WebBackendException(ErrorCode.USER_NOT_FOUND);
        return toCustomData(custom);
    }

    /**
     * 添加顾客
     * test: 99aa7342ba09523f6b3e7ccdbea93fe3b18adeb9
     */
    public CustomData addCustom(CustomFormWithPassword customFormWithPassword) throws Exception {
        Custom custom = new Custom();
        BeanUtils.copyProperties(customFormWithPassword, custom);
        custom.setPassword(EncryptionUtil.encryptPassword(custom.getPassword()));

        //顾客名冲突
        if (customRepository.countByCustomName(custom.getCustomName()) > 0)
            throw new WebBackendException(ErrorCode.USER_EXISTS);

        Custom savedCustom = customRepository.save(custom);
        customRoleRepository.save(generateCustomRoleList(customFormWithPassword.getRoleIdList(), custom.getId()));

        return toCustomData(savedCustom);
    }

    /**
     * 修改顾客, 不包括密码
     */
    public CustomData updateCustom(CustomForm customForm, Long customId) throws Exception {
        Custom custom = customRepository.findById(customId);
        //找不到顾客
        if (custom == null)
            throw new WebBackendException(ErrorCode.USER_NOT_FOUND);

        BeanUtils.copyProperties(customForm, custom, "customName");
        // custom.setPassword(EncryptionUtil.encryptPassword(custom.getPassword()));
        customRoleRepository.deleteByCustomId(customId);
        customRepository.save(custom);
        customRoleRepository.save(generateCustomRoleList(customForm.getRoleIdList(), customId));
        return toCustomData(custom);
    }

    /**
     * 删除顾客
     */
    public void deleteCustom(Long customId) throws Exception {
        Custom custom = customRepository.findById(customId);
        if (custom == null)
            throw new WebBackendException(ErrorCode.USER_NOT_FOUND);
        customRepository.deleteById(customId);
        customRoleRepository.deleteByCustomId(customId);
        customImageRepository.deleteAllByCustomId(customId);
    }

    /**
     * 根据顾客id获取其角色列表
     */
    private List<Role> getRoleList(Long customId) throws Exception {
        List<CustomRole> customRoleList = customRoleRepository.findByCustomId(customId);
        List<Role> roleList = new ArrayList<Role>();
        if (customRoleList == null)
            return null;
        for (CustomRole customRole : customRoleList) {
            roleList.add(roleRepository.findById(customRole.getRoleId()));
        }
        return roleList;
    }

    /**
     * 充值
     */
    public void recharge(Long id, CustomRechargeForm customRechargeForm) throws Exception {

    }

    /**
     * 根据顾客和角色列表插入关联
     */
    private List<CustomRole> generateCustomRoleList(List<Long> roleIdList, Long customId) throws Exception {
        if (roleIdList == null || roleIdList.size() == 0)
            return null;
        List<CustomRole> customRoleList = new ArrayList<CustomRole>();
        for (Long roleId : roleIdList) {
            CustomRole customRole = new CustomRole();
            customRole.setCustomId(customId);
            customRole.setRoleId(roleId);
            customRoleList.add(customRole);
        }
        return customRoleList;
    }

    /**
     * 将Custom转变为CustomData
     */
    private CustomData toCustomData(Custom custom) {
        CustomData customData = new CustomData();
        BeanUtils.copyProperties(custom, customData);
        return customData;
    }

    /**
     * 将Page<Custom>转变为Page<CustomData>
     */
    private Page<CustomData> toCustomDataPage(Page<Custom> customPage, Pageable pageable) throws Exception {
        return ConvertUtil.convertPage(customPage, pageable, customPage.getTotalElements(), new RowConverter<Custom, CustomData>() {
            @Override
            public CustomData convertRow(Custom custom) throws Exception {
                return toCustomData(custom);
            }
        });
    }
}
