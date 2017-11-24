package com.upa.petshop_intern.module.image;

import com.upa.petshop_intern.common.exception.ErrorCode;
import com.upa.petshop_intern.common.exception.WebBackendException;
import com.upa.petshop_intern.common.util.HashCodeUtil;
import com.upa.petshop_intern.entity.CommodityImage;
import com.upa.petshop_intern.entity.ImageStore;
import com.upa.petshop_intern.entity.User;
import com.upa.petshop_intern.entity.UserImage;
import com.upa.petshop_intern.repository.CommodityImageRepository;
import com.upa.petshop_intern.repository.ImageStoreRepository;
import com.upa.petshop_intern.repository.UserImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * Created by Yunhao.Cao on 2017/10/11.
 */
@Service
@Transactional
public class ImageService {

    @Autowired
    ImageStoreRepository imageStoreRepository;

    @Autowired
    CommodityImageRepository commodityImageRepository;

    @Autowired
    UserImageRepository userImageRepository;

    private final int COMMODITY_DEFAULT_IMAGE_NUMBER = 1;

    /**
     * 查找商品图片
     * @param commodityId
     * @param httpServletResponse
     * @throws Exception
     */
    public void findCommodityImage(Long commodityId, HttpServletResponse httpServletResponse) throws Exception {
        CommodityImage commodityImage = commodityImageRepository.findByCommodityId(commodityId);
        if (commodityImage == null) {
            throw new WebBackendException(ErrorCode.QUERY_DATA_EMPTY);
        }
        Long imageId = commodityImage.getImageId();
        this.findImage(imageId, httpServletResponse);
    }

    /**
     * 添加商品图片
     * @param multipartFile
     * @param commodityId
     * @throws Exception
     */
    public void addCommodityImage(MultipartFile multipartFile, Long commodityId) throws Exception {
        if (null == multipartFile) {
            throw new WebBackendException(ErrorCode.FILE_PARAM_IS_REQUIRED);
        }

        Long imageId = this.addImage(multipartFile);

        commodityImageRepository.deleteAllByCommodityId(commodityId);

        CommodityImage commodityImage = new CommodityImage();

        commodityImage.setCommodityId(commodityId);
        commodityImage.setImageId(imageId);
        commodityImage.setImageNumber(COMMODITY_DEFAULT_IMAGE_NUMBER);

        commodityImageRepository.save(commodityImage);
    }

    /**
     * 查找用户图片
     */
    public void findUserImage(Long userId, HttpServletResponse httpServletResponse) throws Exception {
        UserImage userImage = userImageRepository.findByUserId(userId);
        if (userImage == null) {
            throw new WebBackendException(ErrorCode.QUERY_DATA_EMPTY);
        }
        Long imageId = userImage.getImageId();
        this.findImage(imageId, httpServletResponse);
    }

    /**
     * 添加用户头像
     */
    public void addUserImage(MultipartFile multipartFile, Long userId) throws Exception {
        if (null == multipartFile) {
            throw new WebBackendException(ErrorCode.FILE_PARAM_IS_REQUIRED);
        }
        Long imageId = this.addImage(multipartFile);
        userImageRepository.deleteAllByUserId(userId);
        UserImage userImage = new UserImage();
        userImage.setUserId(userId);
        userImage.setImageId(imageId);
        userImage.setImageNumber(COMMODITY_DEFAULT_IMAGE_NUMBER);

        userImageRepository.save(userImage);
    }

    /**
     * 查找图片
     */
    private void findImage(Long id, HttpServletResponse httpServletResponse) throws Exception {
        if (null == id) {
            throw new WebBackendException(ErrorCode.IMAGE_ID_NOT_VALID);
        }

        ImageStore imageStore = imageStoreRepository.findById(id);

        if (null == imageStore) {
            throw new WebBackendException(ErrorCode.QUERY_DATA_EMPTY);
        }

        ServletOutputStream out = httpServletResponse.getOutputStream();

        out.write(imageStore.getImage());

        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 添加图片
     * @return image id
     */
    private Long addImage(MultipartFile multipartFile) throws Exception {
        if (null == multipartFile) {
            throw new WebBackendException(ErrorCode.NOT_VALID_PARAM);
        }

        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        // 是否为图片
        if (bufferedImage == null) {
            throw new WebBackendException(ErrorCode.FILE_NOT_VALID);
        }
        System.out.println(bufferedImage.getType());

        byte[] imageBytes = multipartFile.getBytes();

        String imageHash = HashCodeUtil.getHash(imageBytes);
        if (null == imageHash) {
            throw new WebBackendException(ErrorCode.FILE_NOT_VALID);
        }

        ImageStore existedImageStore = imageStoreRepository.findByHash(imageHash);
        if (null != existedImageStore) {
            return existedImageStore.getId();
        } else {
            String fileName = multipartFile.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            if (suffixName.length() <= 1) {
                throw new WebBackendException(ErrorCode.FILE_NOT_VALID);
            }

            ImageStore imageStore = new ImageStore();
            imageStore.setImage(multipartFile.getBytes());
            imageStore.setFormat(suffixName.replace(".", "").toLowerCase());
            imageStore.setHash(imageHash);

            return imageStoreRepository.save(imageStore).getId();
        }
    }
}
