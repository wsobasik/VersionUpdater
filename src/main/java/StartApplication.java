public class StartApplication {


    public static void main(String[] args) {

        String orgInputFile = args[0];
     //   String orgInputFile = "C:\\workspace\\VersionUpdater\\001\\out\\artifacts\\001_jar\\temp1111_v.11.xml";
        MyInitClass myInitClass = new MyInitClass();
        myInitClass.updateVersionToANewFile(orgInputFile);
        myInitClass.renameTheOutputFile(orgInputFile);


    }

}
