package mn.artlab.law.view.mb.shuukhmn;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ShuukhmnUtil {
    public ShuukhmnUtil() {
        super();
    }

    public static void main(String[] args) {
        System.out.println(retrievePageCount("irgenanhan"));
    }

    public static int retrievePageCount(String courtType) {
        Document htmlDocument = connJsoup("http://shine.shuukh.mn/" + courtType);
        if (htmlDocument == null) {
            return -1;
        }

        Elements imgList = htmlDocument.select("div.shiidver_pagination img[title=Last page]");
        for (Element img : imgList) {
            Element a = img.parent();
            String href = a.attr("href");
            href = href.substring(href.indexOf("page=") + 5);
            return Integer.parseInt(href);
        }

        return 0;
    }

    public static List<String> retrieveDecisionList(String courtType, int page) {
        Document htmlDocument = connJsoup("http://shine.shuukh.mn/" + courtType + "?page=" + page);
        if (htmlDocument == null) {
            return null;
        }

        int prefix = ("/" + courtType + "/").length();
        int postfix = "/view".length();

        List<String> decisionList = new ArrayList<>();
        Elements trList = htmlDocument.select("div#shiidver-list tr.shiidver-row");
        for (Element tr : trList) {
            Element a = tr.select("td > a").get(0);
            String decisionId = a.attr("href");
            decisionId = decisionId.substring(prefix, decisionId.length() - postfix);
            decisionList.add(decisionId);
        }

        return decisionList;
    }

    public static Map<String, String> retrieveDecision(String courtType, String decisionId) {
        Document htmlDocument = connJsoup("http://shine.shuukh.mn/" + courtType + "/" + decisionId + "/view");
        if (htmlDocument == null) {
            return null;
        }

        Elements trList = htmlDocument.select("div#shiidver-detail tbody > tr");
        Map<String, String> m = trList.stream().collect(Collectors.toMap(tr -> tr.child(0).text(), tr -> tr.child(1).text()));

        return m;
    }

    private static Document connJsoup(String url) {
        try {
            Connection conn = Jsoup.connect(url);
            Document htmlDocument = conn.get();
            return htmlDocument;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
