
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import javax.swing.JFileChooser;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.logging.Level;



// MAIN CLASS 
public class Passman {

    // GLOBAL VARIABLES
    private Map<String, ArrayList<String>> details = new LinkedHashMap<String, ArrayList<String>>();
    private Map<String,String> accounts = new HashMap<>();
    private Map<String,String> settings = new HashMap<>();   
    private String key = " "; // initialize them to access in the methods
    private ArrayList<String> values;
    private static String Loginpass , LoginUsername = "sample";

    // ENCRYPTION / DECRYPTION VARIABLES
    private String Encrypteduname = " ";
    private String Encryptedpass = " ";
    private String Decrypteduname = "";
    private String Decryptedpass = "";

    // GLOBAL SCANNER
    static Scanner scan = new Scanner(System.in);

    // INTERNET STATUS
    private static Boolean InternetStatus;

    // TABLE VARIABLES
    private String horTableSep = "-" , verTableSep = "|", whitespace = " " , cornerJoint = "+";














    

    // ENCRYPTION
    private void encrypt(String uname, String pass) 
    {

        int flUname = uname.length(); // length of the username
        int hlUname = flUname / 2; // half lenght of the username
        int flPass = pass.length(); // length of the pass
        int hlPass = flPass / 2; // half length of the password

        char[] unChar = uname.toCharArray(); // storing the passed uname and password into a character sequence to
                                             // process in a loop
        char[] pasChar = pass.toCharArray();
        // --------------------------------------------------------
        // FIRST SHIFT
        String firstShift_uname = ""; // original strings 1st shifted string
        String firstShift_pass = "";

        for (char c : unChar) 
        { // shifting each character from the char seq and storing in firstshifted
            c -= hlUname * 6;
            // shiftuname.add(c);
            firstShift_uname = firstShift_uname + c;
        }
        for (char c : pasChar) 
        {
            c -= hlPass * 6;
            // shiftpass.add(c);
            firstShift_pass = firstShift_pass + c;
        }
        // -------------------------------------------------------
        // SECOND SHIFT
        String secondShift_uname = "";
        String secondShift_pass = "";

        for (char c : unChar) 
        { // shift plus 3
            c += hlUname + 3;
            secondShift_uname = secondShift_uname + c;
        }
        for (char c : pasChar) 
        {
            c += hlPass + 3;
            secondShift_pass = secondShift_pass + c;
        }
        // ---------------------------------------------------------------
        Encrypteduname = ""; // global variables to use in other functions
        Encryptedpass = "";

        Encrypteduname = secondShift_uname + firstShift_uname;
        Encryptedpass = secondShift_pass + firstShift_pass;

    }


    // DECRYPTION
    private void decrypt(String uname, String pass) {

        int flUname = uname.length() / 2; // length of the username
        int hlUname = flUname / 2; // half lenght of the username
        int flPass = pass.length() / 2; // length of the pass
        int hlPass = flPass / 2; // half length of the password

        String tempuname = "";
        String temppass = "";

        tempuname = uname.substring(0, flUname);
        temppass = pass.substring(0, flPass);

        char[] unChar = tempuname.toCharArray(); // storing the passed uname and password into a character sequence to
        char[] pasChar = temppass.toCharArray();
        // --------------------------------------------------------
        // SECOND SHIFT
        String secondShift_uname = "";
        String secondShift_pass = "";

        for (char c : unChar) 
        { // shift plus 3
            c -= hlUname + 3;
            secondShift_uname = secondShift_uname + c;
        }
        for (char c : pasChar) 
        {
            c -= hlPass + 3;
            secondShift_pass = secondShift_pass + c;
        }

        // --------------------------------------------------------------
        Decrypteduname = ""; // global variables to use in other functions
        Decryptedpass = "";

        Decrypteduname = secondShift_uname;
        Decryptedpass = secondShift_pass;

    }


    // SAVES LOGIN DATA
    private void savelogin() throws IOException 
    {
        FileOutputStream fileout = new FileOutputStream(".Tpmaccounts");
        ObjectOutputStream out = new ObjectOutputStream(fileout);
        out.writeObject(accounts);
        out.close();
        fileout.close();
    }


    // LOADS LOGIN DATA 
    private void loadlogin() throws IOException, ClassNotFoundException
    {
        try
        {
            try
            {
                FileInputStream filein = new FileInputStream(".Tpmaccounts");
                ObjectInputStream in = new ObjectInputStream(filein);
                accounts = (Map<String, String>) in.readObject();
                in.close();
                filein.close();
            }
            catch(FileNotFoundException e)
            {
                savelogin();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }


    // SAVES SETTINGS DATA 
    private void saveSettings() throws IOException
    {
        FileOutputStream fileout = new FileOutputStream(".Tpmsettings");
        ObjectOutputStream out = new ObjectOutputStream(fileout);
        out.writeObject(settings);
        out.close();
        fileout.close();
    }

    
    // LOADS SETTINGS DATA
    private void loadSettings() throws IOException, ClassNotFoundException
    {
        try
        {
            try
            {
                FileInputStream filein = new FileInputStream(".Tpmsettings");
                ObjectInputStream in = new ObjectInputStream(filein);
                settings = (Map<String, String>) in.readObject();
                in.close();
                filein.close();
            }
            catch(FileNotFoundException e)
            {
                saveSettings();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }


    // CREATES ACCOUNT
    private void createAcc() throws ClassNotFoundException, IOException 
    {
        clear();
        String loginPass = " ", reloginPass = " ";
         

        System.out.println("Create an Account\n");
        System.out.print("Enter your login Username : ");
        String loginUname = scan.nextLine();

        encrypt(loginUname, loginUname);
        
        if(accounts.containsKey(Encrypteduname))
        {
            System.out.println(ConsoleColors.RED_BRIGHT + "Error : Login Username already exists" + ConsoleColors.RESET);
            System.out.print("Press any key to continue .. ");
            scan.nextLine();
            clear();
            createAcc();
        }
        else
        {
            System.out.print("Enter your login Password : ");
            loginPass = scan.nextLine();
            System.out.print("Re-Enter login Password   : ");
            reloginPass = scan.nextLine();
            /*if(loginPass == null && reloginPass == null)
                {
                    loginPass = " ";
                    reloginPass = " ";
                }*/
        }
        

        if(loginPass.equals(reloginPass))
        {
            
            encrypt(loginUname, loginPass);

            

            accounts.put(Encrypteduname, Encryptedpass);
            savelogin();

            System.out.println(ConsoleColors.TEAL + "Success : Account created , Remember the Username and Password " + ConsoleColors.RESET);
            main(null);
        }
        else 
        {
            System.out.println(ConsoleColors.RED_BRIGHT + "Error : Password did not match " + ConsoleColors.RESET);
            System.out.print("Press any key to continue .. ");
            scan.nextLine();
            clear();
            createAcc();
            
        }

    }


    // FOR CHANGING PASSMAN ACCOUNT PASSWORD
    /*private static void ChangeAccountPass() throws ClassNotFoundException, IOException {
        int response = 1;
        //Random random = new Random();
        Console console = System.console();
        Passman passman = new Passman();
        

        System.out.print("Login Username : ");
        String Username = scan.nextLine();

        if(Username.compareTo(LoginUsername) == 0)
        {  
            passman.encrypt(Username, "");
            

            if(InternetStatus) // --------------- ONLINE ------------------
            { 
                found = (Document) LoginCollection.find(new Document("Login Username",passman.Encrypteduname)).first();

                if(found == null )
                {
                    System.out.println(ConsoleColors.RED_BRIGHT +"Error : Login Username did not match current Username" + ConsoleColors.RESET);
                }
                else
                {
                    if(InternetStatus){ uid = found.getObjectId("_id"); }
                    char[] password = console.readPassword("Login password : ");
                    Loginpass = new String(password);
                    
                } 
                passman.encrypt(LoginUsername, Loginpass);
                foundPassword = (Document)LoginCollection.find(new Document("Login Password",passman.Encryptedpass)).first();

                if(foundPassword == null)
                {
                    System.out.println(ConsoleColors.RED_BRIGHT +"Error : Invalid Login Password " + ConsoleColors.RESET);
                    main(null);
                }
                else
                {
                    
                    pid = foundPassword.getObjectId("_id"); 
                    response = uid.compareTo(pid);
                    if(response == 0)
                    {
                        char[] newpass = console.readPassword("Enter new Account login password : ");
                        String newLoginpass = new String(newpass);  
                        passman.encrypt(Username, newLoginpass);
                        Boolean notfailed = true;
                        try {
                            Document search = new Document().append("Login Username", passman.Encrypteduname);
                            Bson updates = Updates.set("Login Password",passman.Encryptedpass);
                            LoginCollection.updateOne(search, updates);
                            passman.accounts.put(passman.Encrypteduname, passman.Encryptedpass);
                        } 
                        catch (Exception e) {
                            notfailed = false;
                            System.out.println(ConsoleColors.RED + "Error : Process failed " + ConsoleColors.RESET);
                        }   
                        if(notfailed){
                            System.out.println(ConsoleColors.TEAL + "Success : Records updated" + ConsoleColors.RESET);
                        }
                    
                    }
                    
                }

            }
            else
            {
                System.out.println( ConsoleColors.RED + "\nError : [Internet/Database] not connected " + ConsoleColors.RESET);
            }
        }
        else
        {
            System.out.println(ConsoleColors.RED_BRIGHT +"Error : Login Username did not match current Username" + ConsoleColors.RESET);
        }
            
    }*/


    // DISPLAYS AVAILABLE IN TABLE FORM
    private void DisplayAvailable(){
        int sLength = 19;
        

        Set<String> keys = details.keySet();
                    
        for (String key : keys) 
        {
            if(key.length() >= sLength){
                sLength = key.length() + 1;
            }                                                                                 
        }

        System.out.println(" ");
        System.out.println(
            cornerJoint + 
            horTableSep.repeat(sLength+1) + 
            cornerJoint 
        );

        System.out.println(
            verTableSep + ConsoleColors.PURPLE_BACKGROUND +
            " Available services " + ConsoleColors.RESET + whitespace.repeat(sLength - 19) +
            verTableSep
        );

        System.out.println(
            cornerJoint + 
            horTableSep.repeat(sLength+1) + 
            cornerJoint 
        );

        for (String key : keys) 
        {
            System.out.println(
                verTableSep +
                ConsoleColors.PURPLE_ITALIC + " " + key + ConsoleColors.RESET + whitespace.repeat(sLength - key.length()) +
                verTableSep
            );                                                                    
        }

        System.out.println(
            cornerJoint + 
            horTableSep.repeat(sLength+1) + 
            cornerJoint 
        );
                   
    }



    // SAVES THE DATA 
    private void save() throws IOException
    {
        String yesorno = "";
        if (details.isEmpty())
        {
            System.out.println(ConsoleColors.RED_BRIGHT + "\nCAUTION : No data available to save " + ConsoleColors.RESET);
            System.out.print("Do you want to save ? [y/n]: ");
            yesorno = scan.nextLine();

            switch (yesorno)
            {
                case "y":
                    FileOutputStream fileout = new FileOutputStream("."+LoginUsername+"Data");
                    ObjectOutputStream out = new ObjectOutputStream(fileout);
                    out.writeObject(details);
                    out.close();
                    fileout.close();
                    System.out.println(ConsoleColors.TEAL + "\nSuccess : Data saved (Data file empty)" + ConsoleColors.RESET);
                    break;
                case "n":
                    System.out.println("System : Save Terminated");
                    break;
                default:
                    System.out.println(ConsoleColors.RED_BRIGHT + "Error : Invalid option" + ConsoleColors.RESET);
                    break;
            }
        }
        else
        {
            FileOutputStream fileout = new FileOutputStream("."+LoginUsername+"Data");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(details);
            out.close();
            fileout.close();
            System.out.println(ConsoleColors.TEAL + "Success : Data saved" + ConsoleColors.RESET);
        }

    }


    // LOADS THE DATA 
    private void load() throws IOException, ClassNotFoundException 
    {
        try 
        {
            try
            {
                FileInputStream filein = new FileInputStream("."+LoginUsername+"Data");
                ObjectInputStream in = new ObjectInputStream(filein);
                details = (Map<String, ArrayList<String>>) in.readObject();
                in.close();
                filein.close();
                System.out.println(ConsoleColors.TEAL + "Success : Data loaded" + ConsoleColors.RESET);
            } catch (FileNotFoundException e)
            {
                FileOutputStream fileout = new FileOutputStream("."+LoginUsername+"Data");
                ObjectOutputStream out = new ObjectOutputStream(fileout);
                out.writeObject(details);
                out.close();
                fileout.close();
            }
        }
        catch(Exception e) 
        {
            System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Data couldn't load " + e + ConsoleColors.RESET);
        }
    }


    // LOGO 
    public static void logo() 
    {
        System.out.println("" +

                "  ██████   █████  ███████ ███████ ███    ███  █████  ███    ██ " +
                "\n  ██   ██ ██   ██ ██      ██      ████  ████ ██   ██ ████   ██ " +
                "\n  ██████  ███████ ███████ ███████ ██ ████ ██ ███████ ██ ██  ██ " +
                "\n  ██      ██   ██      ██      ██ ██  ██  ██ ██   ██ ██  ██ ██ " +
                "\n  ██      ██   ██ ███████ ███████ ██      ██ ██   ██ ██   ████ " +

                "");

    }


    // ADDS THE DATA TO DB/LOCAL
    private void Add() throws IOException
    {
        
        
        System.out.println("-----------------------------------------------------");
        System.out.print("Service name : "); // servicename is the name of the service in which the username and password will be userd
        String sname = scan.nextLine().trim(); // sname = service name

        if (sname == "")
        {
            System.out.println(ConsoleColors.RED_BRIGHT + "Error : Empty service name" + ConsoleColors.RESET);
        }
        else if (details.containsKey(sname))
        {
            System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Service name already exists" + ConsoleColors.RESET);
        }
        else 
        {
            System.out.print("Email/Username       : ");
            String uname = scan.nextLine().trim(); // uname = username
            System.out.print("Password             : ");
            String pass = scan.nextLine().trim();
            System.out.print("Service address/link : ");
            String link = scan.nextLine().trim();
            if (pass.length() <= 5)
            {
                System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Password is too short" + ConsoleColors.RESET);
            }
            else
            {

                encrypt(uname, pass);
                
                
                
                System.out.println(ConsoleColors.RED + "Error : [Database/Internet] not Connected" + ConsoleColors.RESET);
                
                
                // adding to details object which will be saved in local data file using :
                // save();
                details.put(sname, new ArrayList<String>()); // putting sname and arraylist
                details.get(sname).add(Encrypteduname); // getting the sname and adding uname to it
                details.get(sname).add(Encryptedpass);
                details.get(sname).add(link);

                System.out.println(ConsoleColors.TEAL + "\nSuccess : Record Added" + ConsoleColors.RESET);
                save();

                if(!InternetStatus)
                {
                    settings.put("offlineChanges?", "yes");
                }
                else{
                    settings.put("offlineChanges?", "no");
                }
                saveSettings();

                System.out.println("-----------------------------------------------------");
            }

        }
    }


    // EDITS THE DATA  IN DB/LOCAL
    private void edit() throws IOException
    {
        if (details.isEmpty())
        {
            System.out.println("\nSystem : No records | Try 'add' or 'load'");
        }
        else
        {
            DisplayAvailable();

            System.out.print("\nUpdate : ");
            String sname = scan.nextLine().trim();
            
            

            

            if (details.get(sname) == null)
            {
                System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Invalid service name" + ConsoleColors.RESET + "[" + sname + "]");
            }
            else
            {
                System.out.println("-----------------------------------------------------");
                System.out.print("New Email/Username : ");
                String uname = scan.nextLine();
                System.out.print("New Password       : ");
                String pass = scan.nextLine();
                System.out.print("New Address        : ");
                String link = scan.nextLine();
                System.out.println("");
                encrypt(uname, pass); 
                
                
                //------------------ updating in the local data file----------------------------
              
                if(link != "")
                {
                    //details.get(sname).remove(2); // address
                    details.get(sname).set(2, link);

                }else{
                    System.out.println("System : Address did not update [Local]");
                }

                if(pass != "")
                {
                    //details.get(sname).remove(1); // address
                    details.get(sname).set(1, Encryptedpass);

                }else{
                    System.out.println("System : Password did not update [Local]");
                }
                
                if(uname != "")
                {
                    //details.get(sname).remove(0); // address
                    details.get(sname).set(0, Encrypteduname);

                }else{
                    System.out.println("System : Username did not update [Local]");
                }
                
                     
                
                System.out.println(ConsoleColors.TEAL + "\nSuccess : Record updated" + ConsoleColors.RESET);
                save();

                if(!InternetStatus)
                {
                    settings.put("offlineChanges?", "yes");
                }
                else{
                    settings.put("offlineChanges?", "no");
                }
                saveSettings();

                System.out.println("-----------------------------------------------------");

                
            }
        }
    }


    // REMOVE/DELETES THE DATA FROM DB/LOCAL
    private void remove() throws IOException
    {
        if (details.isEmpty()) 
        {
            System.out.println("\n System : No records | Try 'add' or 'load'");
        }
        else
        {
            Boolean deleted = false ;
            DisplayAvailable();

            System.out.print("\nDelete/Remove : ");
            String sname = scan.nextLine().trim();

            String[] snames = sname.split(",");
            List<String> servicesList = new ArrayList<>(Arrays.asList(snames));
           

            for (int i = 0; i < servicesList.size(); i++)
            {
                // Bson filter (the found document)
                
                
                if ( (!details.containsKey(servicesList.get(i))))
                {
                    System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Invalid service name "+ ConsoleColors.RESET + "[ " + servicesList.get(i) + " ]");
                }
                else
                { 
                    details.remove(servicesList.get(i)); // removes from details object ie locally
                   
            
                    System.out.println("\nSystem : Deleted [ " + servicesList.get(i) + " ]");
                    deleted = true;
                    if (details.isEmpty())
                    {
                        System.out.println("\nSystem : The Record is Empty ");
                    }

                }
            }
            if(deleted == true){
                save();
            }
        }
    }


     // DISPLAYS THE DATA 
     private void display() throws IOException
     {
         Scanner Scan = new Scanner(System.in);
         System.out.println("\n(1) Display all  (2) Display Single/Multiple  (3) Display by Username");
         System.out.print("\nMake your selection : ");
         String display_option = Scan.nextLine();
 
         switch (display_option)
         {
            case "1": // displays all the credentials with the service names
                System.out.println();
                if (details.isEmpty())
                {
                    System.out.println("\nSystem : No records | Try 'add' or 'load'");
                }
                else
                {
                    int sLenMax = 12 , uLenMax = 9 , pLenMax = 9 , aLenMax = 8;
                    for (Map.Entry<String, ArrayList<String>> entry : details.entrySet())
                    {
                        key = entry.getKey();                                    
                        values = entry.getValue();                              
                        decrypt(values.get(0), values.get(1));

                        if(key.length() >= sLenMax){
                            sLenMax = key.length() + 1;
                        }if(Decrypteduname.length() >= uLenMax){
                            uLenMax = Decrypteduname.length() + 1;
                        }if(Decryptedpass.length() >= pLenMax){
                            pLenMax = Decryptedpass.length() + 1;
                        }if(values.get(2).length() >= aLenMax){
                            aLenMax = values.get(2).length() + 1;
                        }

                    }
                    String Records = " Record ";

                    
                    System.out.println(
                                    cornerJoint + 
                                    horTableSep.repeat(Records.length()) +
                                    cornerJoint +
                                    horTableSep.repeat(sLenMax+1) + 
                                    cornerJoint + 
                                    horTableSep.repeat(uLenMax+1) + 
                                    cornerJoint +
                                    horTableSep.repeat(pLenMax+1) + 
                                    cornerJoint + 
                                    horTableSep.repeat(aLenMax+1) +
                                    cornerJoint
                    );
                    System.out.println(
                                        verTableSep + ConsoleColors.TEXT_BACKGROUND +
                                        Records +
                                        verTableSep + 
                                        " Servicename" + whitespace.repeat(sLenMax-11) + //ConsoleColors.RESET +
                                        verTableSep +
                                        " Username" + whitespace.repeat(uLenMax-8) +
                                        verTableSep + //ConsoleColors.CYAN_BACKGROUND + 
                                        " Password" + whitespace.repeat(pLenMax-8) + //ConsoleColors.RESET +
                                        verTableSep + 
                                        " Address" + whitespace.repeat(aLenMax-7) + ConsoleColors.RESET +
                                        verTableSep 

                    );

                    System.out.println(
                                    cornerJoint + 
                                    horTableSep.repeat(Records.length()) +
                                    cornerJoint +
                                    horTableSep.repeat(sLenMax+1) + 
                                    cornerJoint + 
                                    horTableSep.repeat(uLenMax+1) + 
                                    cornerJoint +
                                    horTableSep.repeat(pLenMax+1) + 
                                    cornerJoint + 
                                    horTableSep.repeat(aLenMax+1) +
                                    cornerJoint
                    );

                    int count = 1;
                    for (Map.Entry<String, ArrayList<String>> entry : details.entrySet())
                    {
                        key = entry.getKey();                                     // Servicename 
                        values = entry.getValue();                               // all the data ie 0 = uname , 1 = pass , 2 = link
                        decrypt(values.get(0), values.get(1));
                        
                        System.out.println(
                                    verTableSep + "  " +
                                    count + whitespace.repeat(Records.length() - (Integer.toString(count).length() + 2)) +
                                    verTableSep +
                                    ConsoleColors.PURPLE_ITALIC + " " + key + ConsoleColors.RESET + whitespace.repeat(sLenMax - key.length()) +
                                    verTableSep +
                                    ConsoleColors.WHITE_ITALIC + " " + Decrypteduname + ConsoleColors.RESET + whitespace.repeat(uLenMax - Decrypteduname.length()) +
                                    verTableSep + 
                                    ConsoleColors.CYAN_ITALIC + " " + Decryptedpass + ConsoleColors.RESET + whitespace.repeat(pLenMax - Decryptedpass.length()) +
                                    verTableSep + 
                                    " " + values.get(2) + whitespace.repeat(aLenMax - values.get(2).length()) + 
                                    verTableSep
                        );
                        //System.out.println(" ");
                        count++;
                    }
                    
                    System.out.println(
                                    cornerJoint + 
                                    horTableSep.repeat(Records.length()) +
                                    cornerJoint +
                                    horTableSep.repeat(sLenMax+1) + 
                                    cornerJoint + 
                                    horTableSep.repeat(uLenMax+1) + 
                                    cornerJoint +
                                    horTableSep.repeat(pLenMax+1) + 
                                    cornerJoint + 
                                    horTableSep.repeat(aLenMax+1) +
                                    cornerJoint
                    );

                }
                break;
 
            case "2": // displays only the selected one
                if (details.isEmpty())
                {
                    System.out.println("\nSystem : No records | Try 'add' or 'load'");
                }
                else
                {
                    DisplayAvailable();
 
                    BufferedReader bufread = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("\nDisplay : ");
                    String servicename = bufread.readLine().trim();

                    String[] servicenames = servicename.split(",");
                    List<String> servicesList = new ArrayList<>(Arrays.asList(servicenames));
                    List<String> notfound = new ArrayList<>();

                    for (int i = 0; i < servicesList.size(); i++)
                    {
                        if (!details.containsKey(servicesList.get(i)))
                        {
                            //System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Invalid service name "+ ConsoleColors.RESET + "[ " + servicesList.get(i) + " ]");
                            notfound.add(servicesList.get(i));
                        }
                        else
                        {
                            for (Map.Entry<String, ArrayList<String>> entry : details.entrySet())
                            {
                                String key = entry.getKey();
                                values = entry.getValue();

                                decrypt(values.get(0), values.get(1));

                                if (key.equalsIgnoreCase(servicesList.get(i)))
                                {
                                    System.out.println("\n> " + ConsoleColors.PURPLE + key + ConsoleColors.RESET + "  <" + ConsoleColors.WHITE_ITALIC + Decrypteduname + ConsoleColors.RESET + " | " + ConsoleColors.CYAN_ITALIC + Decryptedpass+ ConsoleColors.RESET + " | " + values.get(2) + ">");
                                }
                            }
                           
                        }
                    }
                    if(!notfound.isEmpty()){
                        System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Invalid Servicenames "+ ConsoleColors.RESET + notfound.toString());
                    }

                }
                break;
 
            case "3":
                if(details.isEmpty())
                {
                    System.out.println("\nSystem : No records | Try 'add' or 'load'");
                }
                else
                {
                    BufferedReader bufread = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("\nUsername? : ");
                    String Username = bufread.readLine().trim();
                    Boolean found = false;

                    
                    for (Map.Entry<String, ArrayList<String>> entry : details.entrySet())
                    {
                        String key = entry.getKey();
                        values = entry.getValue();
                        decrypt(values.get(0), values.get(1));
                        if (Decrypteduname.equalsIgnoreCase(Username))
                        {
                            System.out.println("\n> " + ConsoleColors.PURPLE + key + ConsoleColors.RESET + "  <" + ConsoleColors.WHITE_ITALIC + Decrypteduname + ConsoleColors.RESET + " | " + ConsoleColors.CYAN_ITALIC + Decryptedpass+ ConsoleColors.RESET + " | " + values.get(2) + ">");
                            found = true;
                        }
                    }
                    if(!found)
                    {
                        System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Record not found "+ ConsoleColors.RESET + "[ " + Username + " ]");
                    }
                
                }
                break;

            default:
                System.out.println(ConsoleColors.RED_BRIGHT + "Error : Invalid selection" + ConsoleColors.RESET);
                break;
        }
 
    }


    // COPY TO CLIPBOARD
    private void copy()
    {
        if (details.isEmpty())
        {
            System.out.println("\n System : No records | Try 'add' or 'load'");
        }
        else
        {
            Set<String> keys = details.keySet();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            System.out.println("\n'Select the service name to copy its password to clipboard'");
            DisplayAvailable();
            System.out.print("\nCopy : ");
            String sname = scan.nextLine();

            if (!details.containsKey(sname))
            {
                System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Invalid service name " + ConsoleColors.RESET + "[ "+ sname + " ]");
            }
            else
            {
                for (Map.Entry<String, ArrayList<String>> entry : details.entrySet())
                {
                    String key = entry.getKey();
                    values = entry.getValue();
                    decrypt(values.get(0), values.get(1));

                    if (key.equalsIgnoreCase(sname))
                    {
                        StringSelection selection = new StringSelection(Decryptedpass);
                        clipboard.setContents(selection, selection);
                        System.out.println(ConsoleColors.TEAL + "\nSuccess : {" + sname + "}'s Password has been copied to clipboard "+ ConsoleColors.RESET + "Username: " + Decrypteduname);
                    }
                }
            }

        }

        

    }


     // EXPORTS THE DATA 
    private void export()
     {
         
         System.out.println("\n(1) Export Encrypted  (2) Export Decrypted");
        
         System.out.print("Make your selection : ");
         String ex_selection = scan.nextLine();
 
         if(details.isEmpty())
         {
            System.out.println("\nSystem : No records | Try 'add' or 'load'");
         }
         else
         {

            switch (ex_selection)
            {
                case "1":
                    JFileChooser jFileChooser = new JFileChooser();
                    int respose = jFileChooser.showSaveDialog(null);
                    try 
                    {
                        File location;
                        if (respose == JFileChooser.APPROVE_OPTION)
                        {
                            location = new File(jFileChooser.getSelectedFile().getAbsolutePath());
    
                            List<String> dataArray = new ArrayList<>();
                            int arraycount = 1;
                            if (details.isEmpty())
                            {
                                System.out.println("\nSystem : No records | Try 'add' or 'load'");
                            }
                            else
                            {
                                dataArray.add(0, "" + System.lineSeparator());
    
                                for (Map.Entry<String, ArrayList<String>> entry : details.entrySet())
                                {
                                    key = entry.getKey(); // NOTE: do not specify data type in order to access global variables
                                    values = entry.getValue();
                                    dataArray.add(arraycount, System.lineSeparator() + key + " = " + values.get(0) + " | "+ values.get(1) + " | " + values.get(2));
                                    arraycount++;
                                }
    
                                dataArray.add(arraycount, System.lineSeparator() + "" + System.lineSeparator());
                                Files.writeString(location.toPath(), dataArray.toString());
                                System.out.println(ConsoleColors.TEAL + "\nSuccess : Data exported at " + location+ ConsoleColors.RESET);
                            }
                        }
                        else
                        {
                            System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Export failed" + ConsoleColors.RESET);
                        }
                    } 
                    catch (IOException e) 
                    {
                        System.out.println(ConsoleColors.RED_BRIGHT + "\nError : " + e + ConsoleColors.RESET);
                    }
                    break;
    
                case "2":
                    JFileChooser jFileChooser1 = new JFileChooser();
                    int respose1 = jFileChooser1.showSaveDialog(null);
                    try 
                    {
                        File location;
                        if (respose1 == JFileChooser.APPROVE_OPTION) 
                        {
                            location = new File(jFileChooser1.getSelectedFile().getAbsolutePath());
    
                            List<String> dataArray = new ArrayList<>();
                            int arraycount = 1;
                            if (details.isEmpty()) 
                            {
                                System.out.println("\nSystem : No records | Try 'add' or 'load'");
                            } 
                            else 
                            {
                                dataArray.add(0, "" + System.lineSeparator());
    
                                for (Map.Entry<String, ArrayList<String>> entry : details.entrySet()) 
                                {
                                    key = entry.getKey(); // NOTE: do not specify data type in order to access global variables
                                    values = entry.getValue();
                                    decrypt(values.get(0), values.get(1));
                                    dataArray.add(arraycount, System.lineSeparator() + key + " = " + Decrypteduname + " | "+ Decryptedpass + " | " + values.get(2));
                                    arraycount++;
                                }
    
                                dataArray.add(arraycount, System.lineSeparator() + "" + System.lineSeparator());
                                Files.writeString(location.toPath(), dataArray.toString());
                                System.out.println(ConsoleColors.TEAL + "\nSuccess : Data exported at " + location+ ConsoleColors.RESET);
                            }
                        } 
                        else 
                        {
                            System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Export failed" + ConsoleColors.RESET);
                        }
                    } 
                    catch (IOException e) 
                    {
                        System.out.println(ConsoleColors.RED_BRIGHT + "\nError : " + e + ConsoleColors.RESET);
                    }
                    break;
    
                default:
                    System.out.println(ConsoleColors.RED_BRIGHT + "Error : Invalid selection " + ConsoleColors.RESET + "["+ ex_selection + "]");
                    break;
            }
 

        }
         
    }


     // CLEAR TERMINAL SCREEN 
    private void clear()
    {
        try
        {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } 
        catch (Exception e)
        {
            System.out.println("Cannot clear screen at the moment!");
        }
    }


    // HELP 
    public static void help()
    {
        
        System.out.println("\n> add     - To ADD credentials\n");
        System.out.println("> edit    - To EDIT existing credentials, 'NOTE - Service name is not editable!'\n");
        System.out.println("> display - To DISPLAY existing credentials\n");
        System.out.println("> remove  - To REMOVE any existing credentials\n");
        System.out.println("> copy    - To COPY the password to clipboard \n");
        System.out.println("> export  - To EXPORT the saved data(Encrypted or Decrypted) into any type of text file (specify file extension)\n");
        System.out.println("> scan    - To SCAN the credentials and give a detailed report\n");
        System.out.println(" ");
        System.out.println("> clear   - To CLEAR the terminal screen\n");
        System.out.println("> help    - Congrats you understood this one!\n");
        System.out.println("> save    - To SAVE the data\n");
        System.out.println("> load    - To LOAD the saved data\n");
        System.out.println("> exit    - To EXIT the program\n");
        System.out.println(" ");
        System.out.println("> reset   - To CHANGE the login password of your account (current)\n");
       
    }




    // ----------------------------------MAIN METHOD------------------------------------
    public static void main(String[] args) throws IOException, ClassNotFoundException 
    {
        
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF); // logger content turned off
        Console console = System.console();
       
        Passman passman = new Passman();
        String ch;

        
        passman.loadlogin();

        while (true)
        {
            passman.clear();
            int response = 1;
            passman.loadlogin();
            System.out.println("");
            System.out.println(ConsoleColors.RED_BACKGROUND + " OFFLINE MODE " + ConsoleColors.RESET );
            

            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "\nLogin username : " + ConsoleColors.RESET);
            LoginUsername = scan.nextLine();
            passman.encrypt(LoginUsername, "");

            
            if(!passman.accounts.isEmpty())
            {
                if(!passman.accounts.containsKey(passman.Encrypteduname))
                {
                    System.out.println(ConsoleColors.RED_BRIGHT +"Error : Invalid Login Username" + ConsoleColors.RESET);
                    System.out.println("\nSystem : Try again with Internet connection..");
                    System.out.print("Press any key to continue..");
                    scan.nextLine();
                    main(null);
                }
                else
                {
                    char[] password = console.readPassword(ConsoleColors.GREEN_BOLD_BRIGHT + "Login password : " + ConsoleColors.RESET);
                    Loginpass = new String(password);
                    passman.encrypt(LoginUsername, Loginpass);
                    if(passman.accounts.get(passman.Encrypteduname).compareTo(passman.Encryptedpass) != 0)
                    {
                        System.out.println(ConsoleColors.RED_BRIGHT +"Error : Invalid Login Password " + ConsoleColors.RESET);
                        main(null);
                    }
                } 
                
            }
            else
            {
                System.out.print(ConsoleColors.RED_BRIGHT + "Error : Account not found" + ConsoleColors.RESET);
                System.out.println("\nSystem : Try again with Internet connection..");
                System.out.print("Press any key to continue..");
                scan.nextLine();
                main(null);
                break;
                
            }
            

            // ------------------Login-----------------------

            try 
            {
                if (response == 0 || passman.accounts.get(passman.Encrypteduname).compareTo(passman.Encryptedpass) == 0 ) 
                {
                    System.out.println();
                    System.out.println("  Welcome to");
                    logo();
                    System.out.println("\nFor a list of available commands, type 'help'.");
                    System.out.println("--------------------------------------------------------");
                    passman.load();
                    passman.savelogin();
                    passman.loadSettings();

            
                    System.out.println(ConsoleColors.TEAL + "Success : Local Database Connected " + ConsoleColors.RESET);
                    
                    System.out.println("--------------------------------------------------------");


                    if(passman.settings.isEmpty())
                    {
                        if(!InternetStatus)
                        {
                            passman.settings.put("offlineChanges?", "yes");
                        }
                        else
                        {
                            passman.settings.put("offlineChanges?", "no");
                        }

                    }
                    passman.saveSettings();


                    while (true) 
                    {

                        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "\nPassman > " + ConsoleColors.RESET);
                        ch = scan.nextLine();
                    
                        passman.loadSettings();

                        if(InternetStatus)
                        {
                            if(passman.settings.get("offlineChanges?").compareTo("yes")==0)
                            {
                                
                                passman.settings.put("offlineChanges?", "no");
                                passman.saveSettings();
                            }
                           

                        }



                        switch (ch.trim().toLowerCase()) 
                        {
                            case "exit":
                                System.out.println(ConsoleColors.RED_BACKGROUND + "\nProgram Exited!\n" + ConsoleColors.RESET);
                                System.exit(0);
                                break;
                            case "add":
                                passman.Add();
                                break;
                            case "remove":
                                if(InternetStatus){passman.remove();}
                                else{System.out.println(ConsoleColors.RED_BRIGHT + "\nError : Remove command requires [Internet/Database] connectivity " + ConsoleColors.RESET);}
                                break;
                            case "edit":
                                passman.edit();
                                break;
                            case "display": 
                                passman.display();
                                break;
                            case "help":
                                help();
                                break;
                            case "save":
                                passman.save();
                                break;
                            case "load":
                                passman.load();
                                break;
                            case "clear":
                                passman.clear();
                                break;
                            case "logo":
                                logo();
                                break;
                            case "export":
                                passman.export();
                                break;
                            case "about":
                                System.out.println("This is a Terminal Password Manager");
                                break;
                            case "copy":
                                passman.copy();
                                break;
                            case "scan":
                                //statusChecker.status(passman.details);
                                break;
                            default:
                                System.out.println(ConsoleColors.RED_BRIGHT + "Error : Invalid command"+ ConsoleColors.RESET + "[" + ch + "]");
                                break;
                        }
                    }
                } 
                else 
                {
                    System.out.println(ConsoleColors.RED_BRIGHT + "Error : Invalid login [Username/Password]" + ConsoleColors.RESET);
                }
            } 
            catch (NullPointerException e) {
                System.out.println(ConsoleColors.RED_BRIGHT + "Error : Invalid login [Username/Password]" + ConsoleColors.RESET);
                System.out.println(e);
            }
    
        }
    }
}
