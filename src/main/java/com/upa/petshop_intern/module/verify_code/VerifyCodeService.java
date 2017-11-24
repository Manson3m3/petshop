package com.upa.petshop_intern.module.verify_code;

import com.google.code.kaptcha.Producer;
import com.upa.petshop_intern.common.util.VerifyCodeUtil;
import com.upa.petshop_intern.entity.VerifyRecord;
import com.upa.petshop_intern.repository.VerifyRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.util.Calendar;


/**
 * Created by Yunhao.Cao on 2017/10/10.
 */
@Service
@Transactional
public class VerifyCodeService {

    @Autowired
    VerifyRecordRepository verifyRecordRepository;

    @Value("${anonymous-verify-code-overdue}")
    int VERIFY_CODE_OVER_DUE;

    /**
     * 获取图片信息写入response
     *
     * @param response 将图片信息写入response
     * @return 验证码实际值
     * @throws Exception
     */
    public String setVerifyCodeImage(HttpServletResponse response) throws Exception {
        Producer producer = VerifyCodeUtil.captchaProducer();

        //生产验证码字符串
        String createText = producer.createText();

        //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
        BufferedImage bufferedImage = producer.createImage(createText);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }

        VerifyCodeUtil.writeHeaderCache(response);

        return createText;
    }

    public void addVerifyRecord(String verifyCode, String verifyToken) throws Exception {

        VerifyRecord verifyRecord = new VerifyRecord();

        verifyRecord.setVerifyToken(verifyToken);
        verifyRecord.setVerifyCode(verifyCode);

        verifyRecord.setOverdueTime(new Timestamp(System.currentTimeMillis() + VERIFY_CODE_OVER_DUE));

        verifyRecordRepository.save(verifyRecord);
    }
}
