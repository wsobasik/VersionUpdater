import java.io.*;

public class MyInitClass {


    private final String PREFIX = "_v.";
    private final String EXTENTION = ".xml";
    private String orgInputFile ;
    private String tmpFile = System.getProperty("user.dir") + "\\tempTmp.xml";
    private Long version;
    private String outputFile;


    public void readFileToBuffor(String orgInputFile) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tmpFile))) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(orgInputFile))) {
                String currentLine;
                String newLine;
                int lineNumber = 0;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    lineNumber++;
                    if ((currentLine.contains("version=")) & (lineNumber > 1)) {
                        newLine = increseVersionNumberAndReturnALine(currentLine);
                    } else {
                        newLine = currentLine;
                    }
                    bufferedWriter.write(newLine);
                    bufferedWriter.newLine();
                    //System.out.println(newLine);
                }

//                outputFile = orgInputFile.substring(0, orgInputFile.length() - 4) + PREFIX + getVersion() + ".xml";/**/
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

            int versionLenght =  version.toString().length();
        if (orgInputFile.contains(PREFIX)){
            int endIndex = PREFIX.length() + versionLenght+ EXTENTION.length();
            outputFile = orgInputFile.substring(0, orgInputFile.length() - endIndex) + PREFIX + getVersion() + EXTENTION;
        }
        else
        {
            int endIndex = EXTENTION.length();

            outputFile = orgInputFile.substring(0, orgInputFile.length() - endIndex) + PREFIX + getVersion() + EXTENTION;
        }
        File file1 = new File(tmpFile);
        File file2 = new File(outputFile);
        if (file2.exists()) {
            try {

                throw new IOException("file exists");
            } catch (IOException e) {
                System.out.println("Plik juz istnieje");
                e.printStackTrace();
            }
        } else {
            boolean success = file1.renameTo(file2);

            if (success) {
                file1.delete();
            }
        }
    }


    private String increseVersionNumberAndReturnALine(String currentLine) {
        String[] splittedCurrentLine = currentLine.split("version=");
        int firstQuatationMark = 1;
        String beforeVersionSubString = splittedCurrentLine[0];
        String afterVersionSubString = splittedCurrentLine[1];
        int secondQuatationMark = afterVersionSubString.indexOf("\"", 1);
        String versionAsString = afterVersionSubString.substring(firstQuatationMark, secondQuatationMark);
        Long updatedVersion = Long.parseLong(versionAsString) + 1;
        setVersion(updatedVersion);
        String currentLineWithUpdatedVersion = beforeVersionSubString + "version=" + "\"" + updatedVersion.toString() /*+ "\""*/ + afterVersionSubString.substring(secondQuatationMark);
        return currentLineWithUpdatedVersion;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
