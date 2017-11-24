package com.upa.petshop_intern.module.category;

import com.upa.petshop_intern.common.exception.ErrorCode;
import com.upa.petshop_intern.common.exception.WebBackendException;
import com.upa.petshop_intern.common.page.PageParams;
import com.upa.petshop_intern.entity.Category;
import com.upa.petshop_intern.entity.Commodity;
import com.upa.petshop_intern.entity.CommodityCategory;
import com.upa.petshop_intern.repository.CategoryRepository;
import com.upa.petshop_intern.repository.CommodityCategoryRepository;
import com.upa.petshop_intern.repository.CommodityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Li.Hou on 2017/10/11.
 */
@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommodityCategoryRepository commodityCategoryRepository;
    @Autowired
    private CommodityRepository commodityRepository;
    /**
     * 添加品类
     * @param category
     * @throws Exception
     */
    public void addCategory(Category category) throws Exception {
        if (category == null||StringUtils.isEmpty(category.getCategoryName())) {
            throw new WebBackendException(ErrorCode.OTHER);
        }
        if (categoryRepository.findByCategoryName(category.getCategoryName()) != null) {
            throw new WebBackendException(ErrorCode.CATEGORY_EXISTS);
        }
        Category targetCategory = new Category();
        BeanUtils.copyProperties(category,targetCategory,"id");
        categoryRepository.save(targetCategory);
    }

    /**
     * 删除品类
     * @param categoryId
     * @throws Exception
     */
    public void deleteCategory(Long categoryId) throws Exception {

        if (categoryRepository.findById(categoryId) == null) {
            throw new WebBackendException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(categoryId);
        commodityCategoryRepository.deleteByCategoryId(categoryId);
    }

    /**
     * 更新品类
     * @param category
     * @throws Exception
     */
    public void updateCategory(Category category,Long id) throws Exception {
        if (null == categoryRepository.findById(id)) {
            throw new WebBackendException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        if (null==category||StringUtils.isEmpty(category.getCategoryName())) {
            throw new WebBackendException(ErrorCode.OTHER);
        }
        if ((!category.getCategoryName().equals(categoryRepository.findById(id).getCategoryName()))&&categoryRepository.countByCategoryName(category.getCategoryName())>0) {
            throw new WebBackendException(ErrorCode.CATEGORY_EXISTS);
        }
        category.setId(id);
        categoryRepository.save(category);
    }

    /**
     * 根据id查找品类
     * @param categoryId
     * @return
     * @throws Exception
     */
    public Category findByCategoryId(Long categoryId) throws Exception{
        if (null == categoryRepository.findById(categoryId)) {
            throw new WebBackendException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return categoryRepository.findById(categoryId);
    }

    /**
     * 展示商品分页
     * @param pageable
     * @return
     * @throws Exception
     */

    public Page<Category> findAllCategories(Pageable pageable) throws Exception {
        return categoryRepository.findAll(pageable);
    }

    /**
     * 根据品类名称模糊搜索
     * @param categoryName
     * @param pageable
     * @return
     * @throws Exception
     */
    public Page<Category> findAllByCategorySearchName(String categoryName, Pageable pageable)throws Exception{
        return categoryRepository.findByCategoryNameLike(categoryName,pageable);
    }

    /**
     * 展示品类的所有商品
     * @param categoryId
     * @return
     * @throws Exception
     */
    public Page<Commodity> findAllCommoditiesByCategoryId(Long categoryId,Pageable pageable) throws Exception{
        if (categoryRepository.findById(categoryId) == null) {
            throw new WebBackendException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        if (commodityCategoryRepository.findAllByCategoryId(categoryId) == null) {
            throw new WebBackendException(ErrorCode.CATEGORY_HAVE_NO_COMMODITY);
        }
        List<Commodity> commodityList = new ArrayList<>();
        for (CommodityCategory commodityCategory:
             commodityCategoryRepository.findAllByCategoryId(categoryId)) {
            commodityList.add(commodityRepository.findById(commodityCategory.getCommodityId()));
        }
        return new PageImpl<>(commodityList,pageable,commodityList.size());
    }

    /**
     * 根据商品id查询品类列表
     * @param commodityId
     * @return
     * @throws Exception
     */
    public List<Category> findAllCategoriesByCommodityId(Long commodityId) throws Exception {
        if (commodityRepository.findById(commodityId) == null) {
            throw new WebBackendException(ErrorCode.COMMODITY_NOT_FOUND);
        }
        if (commodityCategoryRepository.findAllByCommodityId(commodityId) == null) {
            return null;
        }
        List<Category> categoryList = new ArrayList<>();
        for (CommodityCategory commodityCategory :
                commodityCategoryRepository.findAllByCommodityId(commodityId)) {
            categoryList.add(categoryRepository.findById(commodityCategory.getCategoryId()));

        }
        return categoryList;
    }
}
