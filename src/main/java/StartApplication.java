public class StartApplication {


    public static void main(String[] args) {

        String orgInputFile = args[0];
        String tenantName = null;
        MyInitClass myInitClass = new MyInitClass();


        if (args.length > 1) {
            tenantName = args[1];
            myInitClass.updateVersionToANewFile(orgInputFile,tenantName);
            myInitClass.renameTheOutputFile(orgInputFile);
        } else {
            myInitClass.updateVersionToANewFile(orgInputFile);
            myInitClass.renameTheOutputFile(orgInputFile);
        }
    }
}
// tenantBusinessKey="IT5"
// tenant business-key="IT5"