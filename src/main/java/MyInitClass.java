import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyInitClass {

    private final String PREFIX = "_v(\\d+).xml";
    private String tmpFile = System.getProperty("user.dir") + "\\tempTmp.xml";
    private Long version;
    Pattern versionPattern = Pattern.compile("version=\"(\\d+)\"");
    Pattern fileVersionPattern = Pattern.compile(PREFIX);
    Pattern tenantBKPattern1 = Pattern.compile("tenantBusinessKey=\"\\w+\"");
    Pattern tenantBKPattern2 = Pattern.compile("<tenant.*business-key=\"\\w+\".*");


    public void updateVersionToANewFile(String orgInputFile) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(tmpFile), StandardCharsets.UTF_8));


                BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(new FileInputStream(orgInputFile),StandardCharsets.UTF_8));
        ) {
            String currentLine;
            String newLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                newLine = increseVersionNumber(currentLine);
                bufferedWriter.write(newLine);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createUpdatedFileName(String orgInputFile) {
        String outputFile;
        Matcher matcher = fileVersionPattern.matcher(orgInputFile);

        if (matcher.find()) {
            return matcher.replaceAll("_v" + getVersion() + ".xml");

        } else {
            return orgInputFile.replace(".xml", "_v" + getVersion() + ".xml");
        }
    }

    public void renameTheOutputFile(String orgInputFile) {

        String outputFile = createUpdatedFileName(orgInputFile);
        File file1 = new File(tmpFile);
        File file2 = new File(outputFile);
        if (file2.exists()) {
            outputFile = outputFile.replace(".xml","(1).xml");}
            file2 = new File(outputFile);
            if (file2.exists()){
            try {
                throw new IOException("file exists");
            } catch (IOException e) {
                System.out.println("Plik juz istnieje");
            }
        } else {
            boolean success = file1.renameTo(file2);
            if (success) {
                file1.delete();
            } else {
                System.out.println("Nie udalo sie zmienic nazwy.");
            }
        }
    }


    private String increseVersionNumber(String currentLine) {
        Long updatedVersion;
        Matcher matcher = versionPattern.matcher(currentLine);
        if (matcher.find()) {
            updatedVersion = Long.parseLong(matcher.group(1)) + 1;
            setVersion(updatedVersion);
            return matcher.replaceAll("version=\"" + updatedVersion + "\"");
        } else {
            return currentLine;
        }
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {

        this.version = version;
    }

    public void updateVersionToANewFile(String orgInputFile, String tenantName) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tmpFile));
                BufferedReader bufferedReader = new BufferedReader(new FileReader(orgInputFile))
        ) {
            String currentLine;
            String newLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                newLine = increseVersionNumber(currentLine);
                newLine = changeTenantName(newLine, tenantName);
                bufferedWriter.write(newLine);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String changeTenantName(String currentLine, String tenantName ) {
        Matcher matcher1 = tenantBKPattern1.matcher(currentLine);
        Matcher matcher2 = tenantBKPattern2.matcher(currentLine);
        if (matcher1.find()) {
            return matcher1.replaceAll("tenantBusinessKey=\"" + tenantName + "\"");
        }
        if (matcher2.matches()) {
            return currentLine.replaceAll("business-key=\"\\w+\"","business-key=\"" + tenantName + "\"");
            }
            return currentLine;

    }
}