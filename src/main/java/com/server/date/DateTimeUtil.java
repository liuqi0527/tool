package com.server.tool.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author LiuQi - [Created on 2018-05-24]
 */
public class DateTimeUtil {


    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.now();

        //@formatter:off

        System.out.println("DateTimeFormatter.toString            :" + dateTime.toString());
        System.out.println("DateTimeFormatter.BASIC_ISO_DATE      :" + dateTime.format(DateTimeFormatter.BASIC_ISO_DATE           ));
        System.out.println("DateTimeFormatter.ISO_DATE            :" + dateTime.format(DateTimeFormatter.ISO_DATE                 ));
        System.out.println("DateTimeFormatter.ISO_DATE_TIME       :" + dateTime.format(DateTimeFormatter.ISO_DATE_TIME            ));
//        System.out.println("DateTimeFormatter.ISO_INSTANT         :" + dateTime.format(DateTimeFormatter.ISO_INSTANT              ));
        System.out.println("DateTimeFormatter.ISO_LOCAL_DATE      :" + dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE           ));
        System.out.println("DateTimeFormatter.ISO_LOCAL_DATE_TIME :" + dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME      ));
//        System.out.println("DateTimeFormatter.ISO_OFFSET_DATE_TIME:" + dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME     ));
//        System.out.println("DateTimeFormatter.ISO_ZONED_DATE_TIME :" + dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME      ));
        System.out.println("DateTimeFormatter.ISO_WEEK_DATE       :" + dateTime.format(DateTimeFormatter.ISO_WEEK_DATE            ));
//        System.out.println("DateTimeFormatter.RFC_1123_DATE_TIME  :" + dateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME       ));

        System.out.println("DateTimeFormatter.yyyy-MM-dd HH:mm:ss : " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("DateTimeFormatter.yyyy-MM-dd HH:mm:ss.SSS : " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));


//DateTimeFormatter.ISO_LOCAL_DATE_TIME :2018-05-24T12:01:50.253
            //@formatter:on
    }
}
