package com.dslcode.shiro.config.jcaptcha;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

import java.awt.*;

/**
 * Created by dongsilin on 2017/5/13.
 */
public class SampleListImageCaptchaEngine extends ListImageCaptchaEngine {

    @Override
    protected void buildInitialFactories() {
        // 随机生成的字符
        WordGenerator wordGenerator = new RandomWordGenerator("0123456789");
        // 文字显示的个数
        TextPaster textPaster = new RandomTextPaster(
                4, 5,
                new RandomListColorGenerator(
                        new Color[]{
                                new Color(23, 170, 27),
                                new Color(220, 34, 11),
                                new Color(23, 67, 172)
                        })
        );
        // 图片的大小
        BackgroundGenerator backgroundGenerator = new FunkyBackgroundGenerator(80, 30);
        // 字体格式
        Font[] fontsList = new Font[] {
                new Font("Arial", Font.BOLD, 10),
                new Font("Tahoma", Font.PLAIN, 10),
                new Font("Verdana", Font.BOLD, 10)
        };
        // 文字的大小
        FontGenerator fontGenerator = new RandomFontGenerator(16, 20, fontsList);

        WordToImage wordToImage = new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster);
        addFactory(new GimpyFactory(wordGenerator, wordToImage));
    }

}
