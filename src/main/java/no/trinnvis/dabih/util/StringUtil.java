package no.trinnvis.dabih.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StringUtil {
    public static final Locale locale = Locale.forLanguageTag("nb-NO");
    public static final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);

    /**
     * Join strings to s single string
     *
     * @param args
     * @return
     */
    public static String concat(String... args) {
        StringBuilder r = new StringBuilder();
        for (String s : args) {
            if (s != null) {
                r.append(" ").append(s);
            }
        }
        return r.toString().trim();
    }

    /**
     * check if the string is empty
     *
     * @param p
     * @return
     */
    public static boolean isEmpty(String p) {
        return p == null || "".equals(p.trim());
    }

    /**
     * convert Boolean value to boolean value.
     *
     * @param b
     * @return
     */
    public static boolean valueOf(Boolean b) {
        return b != null && b;

    }

    /**
     * parse html text to get the link text
     *
     * @param content
     * @return
     */
    public static String parseHtmlToLink(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }
        Document doc = Jsoup.parse(content);
        Elements links = doc.getElementsByTag("a");
        String linkHref = null;
        for (Element link : links) {
            linkHref = link.attr("href");
        }
        return linkHref;
    }

    /**
     * parse html text for specific class
     *
     * @param content
     * @param className
     * @return
     */
    @SuppressWarnings("SameParameterValue")
    public static String parseHtmlForSpecificClass(String content, String className) {
        if (content == null || content.isEmpty() || !content.contains(className)) {
            return null;
        }
        Document doc = Jsoup.parse(content);
        Elements des = doc.getElementsByClass(className);
        StringBuilder childs = new StringBuilder();
        for (Element doe : des) {
            for (Element child : doe.children()) {
                childs.append(child.toString());
            }
        }
        return childs.toString();
    }

    /**
     * generate file with unique file name
     *
     * @param path
     * @param name
     * @return
     * @throws UnsupportedEncodingException
     */
    public static File uniqueFile(String path, String name) throws UnsupportedEncodingException {

        String baseName = FilenameUtils.getBaseName(name);
        String fileExtension = FilenameUtils.getExtension(name);
//        DateFormat df = new SimpleDateFormat("dd.MM.yy-HHmmss");

//        return new File(path, URLEncoder.encode(baseName, "UTF-8") + "-" + df.format(new Date()) + "." + fileExtension);
        return new File(path, URLEncoder.encode(baseName, "UTF-8") + "." + fileExtension);
    }

    /**
     * generate a unique file name
     *
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String uniqueFileName(String fileName, String inputFileExtension) throws UnsupportedEncodingException {
        String baseName = FilenameUtils.getBaseName(fileName);
        String fileExtension = FilenameUtils.getExtension(fileName);

        if (fileExtension == null || fileExtension.isEmpty()) {
            fileExtension = inputFileExtension;
        }
        DateFormat df = new SimpleDateFormat("dd.MM.yy-HHmmss");

        return URLEncoder.encode(baseName, "UTF-8") + "-" + df.format(new Date()) + "." + fileExtension;
    }

    /**
     * generate specific string for date
     *
     * @param startDate
     * @return
     */
    public static String processEndDate(Date startDate) {
        Date endDate = new Date();
        long diff = endDate.getTime() - startDate.getTime();
        return DateUtil.formatDateTime(endDate, DateUtil.DATETIME_FORMAT) + ". used " + String.valueOf(diff) + " ms";
    }

    /**
     * parse null string to empty
     *
     * @param s
     * @return
     */
    public static String parseNullOrEmpty(String s) {
        if (s == null) {
            return "";
        }
        return s.trim();
    }

    /**
     * parse file length parameter to int
     *
     * @param input
     * @return
     */
    public static int parseStringToInter(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(input);
    }
}

