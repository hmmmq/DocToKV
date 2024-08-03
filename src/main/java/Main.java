import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

class FilePath {
    public static final String FILE_PATH = "src/main/resources/1.docx";
}
class Title{
    public static final String TITLE = "一、二、三、四、五、六、七、八、九、十";

}

class ReadIO {
    Map<String, ArrayList<String>> map = new LinkedHashMap<>();

    public void read() {
        System.out.println("Reading from input stream");

        try (FileInputStream fis = new FileInputStream(FilePath.FILE_PATH);
             XWPFDocument document = new XWPFDocument(fis)) {
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            String currentTitle = null;
            ArrayList<String> contentList = null;

            for (XWPFParagraph paragraph : paragraphs) {
                String text = paragraph.getText().trim();
                if (Title.TITLE.indexOf(text.charAt(0))!=-1) {
                    //包含标题关键词
                    currentTitle = text;
                    map.put(text,new ArrayList<>());

                } else {
                    //不包含标题关键词
                    if(currentTitle!=null){
                        map.get(currentTitle).add(text);
                    }else{
                        currentTitle = "文档标题";
                        map.put(currentTitle,new ArrayList<>());
                        map.get(currentTitle).add(text);
                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, ArrayList<String>> getMap() {
        return map;
    }
}

public class Main {
    public static void main(String[] args) {
        ReadIO readIO = new ReadIO();
        readIO.read();

        // Print the map to see the result
        Map<String, ArrayList<String>> resultMap = readIO.getMap();
        for (Map.Entry<String, ArrayList<String>> entry : resultMap.entrySet()) {
            System.out.println("KEY: " + entry.getKey());
            System.out.println("VALUE: " + entry.getValue());
        }
    }
}
