
import java.sql.*;
import java.util.Scanner;

public class JDBC_Project1
{
    public static void main(String[] arg) throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Scanner scanner = new Scanner(System.in);
        String username, password;

        do
        {
            System.out
                    .println("Please enter username and password (or leave both fields blank to exit the program).");
            System.out.print("Username: ");
            username = scanner.nextLine();

            System.out.print("Password: ");
            password = scanner.nextLine();

            if (username.equals("") && password.equals("")) break;

            // Connection connection =
            // DriverManager.getConnection("jdbc:mysql:///moviedb",username,
            // password);
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://", username, password);

            System.out.println("Connected to MySQL");

            String db;
            ResultSet rs = null;
            Statement st = connection.createStatement();
            rs = st.executeQuery("show databases;");
            ResultSetMetaData md = rs.getMetaData();
            System.out.println("Avialiable Databases: ");
            while (rs.next())
            {
                System.out.println("    " + rs.getString(1));
            }


            System.out.print("Enter which to use database: ");
            db = scanner.nextLine();
            String useDB = "use " + db + ";";
            rs = st.executeQuery("use " + db + ";");
            System.out.println();
            System.out.println("Connected to " + db);
            
            
            if (db.equals("moviedb"))
            {
            
            String command = null;
            String commandList = "Command List: \n0 : shows command list\n"
                    + "1 : print out movies feturing a given star\n"
                    + "2 : insert a new star to database\n"
                    + "3 : add a new customer to database\n"
                    + "4 : delete a customer from database\n"
                    + "5 : provides meta of the database\n"
                    + "6 : excute a valid SQL command\n"
                    + "7 : exit the menu\n"
                    + "x : Database Administrator Management";  
            do
            {
                System.out.print("Please enter a command(enter '0' for command list): ");

                command = scanner.nextLine();

                if (command.equals("x"))
                {
                    System.out.println();
                    System.out.println("Database Administor Mangement Menu");
                    
                    String dbaCommand;
                    String dbaList = "DBA Command List:\n"
                            + "  1 : show resources\n"
                            + "  2 : user management\n"
                            + "  x : exit DBA management";
                  
                    do
                    {
                        System.out.println(dbaList);
                        System.out.print("Choose a command from the DBA command list: ");
                        dbaCommand = scanner.nextLine();
                        System.out.println();
                        
                        if (dbaCommand.equals("1"))
                        {
                            
                            ResultSet resourceResult;
                            Statement resourceStatement = connection.createStatement();
                            
                            String resourceCommand = null;
                            String resourceList = "DBA Resources Command List:\n"
                                            + "  1 : show tables\n"
                                            + "  2 : show columns from tables\n"
                                            + "  3 : show procedures in database\n"
                                            + "  x : exit";

                            do
                            {                            
                                System.out.println(resourceList);
                                System.out.print("Choose a command from the DBA Resources command list: ");
                                resourceCommand = scanner.nextLine();
                                
                                if(resourceCommand.equals("1"))
                                {
                                   System.out.println();
                                   resourceResult = resourceStatement.executeQuery("show tables;");  
                                   System.out.println("Tables in moviedb: ");
                                   while(resourceResult.next())
                                   {
                                       System.out.println("  " + resourceResult.getString(1));
                                   }
                                   
                                   System.out.println();                          
                                }
                                
                                if (resourceCommand.equals("2"))
                                {
                                    System.out.println("\nChoose a table to get columns from: ");
                                    String commandTable = scanner.nextLine();   
                                    resourceResult = resourceStatement.executeQuery("show columns from " + commandTable + ";");  
                                    System.out.println("\nColumns from " + commandTable + ": ");
                                    while(resourceResult.next())
                                    {
                                        System.out.println("  " + resourceResult.getString(1));
                                    }                    
                                }
                                
                                if (resourceCommand.equals("3"))
                                {
                                    resourceResult = resourceStatement.executeQuery("show procedure status;");  
                                    System.out.println("\nProcedures in " + db + ": ");
                                    while(resourceResult.next())
                                    {
                                        System.out.println("  " + resourceResult.getString(2));
                                    }                    
                                }
                                
                          
                             System.out.println();
                            }while(!resourceCommand.equals("x"));  
                        }
                        
                        if (dbaCommand.equals("2"))
                        {
                            ResultSet userResult;
                            Statement userStatement = connection.createStatement();
                            
                            String userCommand = null;
                            String userList = "\nUser Management Command List: "
                                             +"\n  1 : show users"
                                             +"\n  2 : add user"
                                             +"\n  3 : delete user"
                                             +"\n  4 : show privalages on users "
                                             +"\n  5 : grant users privaleges"
                                             +"\n  6 : take away user privaleges"
                                             +"\n  x : exit";
                            do
                            {
                                System.out.println(userList);
                                System.out.print("Choose a command from the User Management command list: ");
                                userCommand = scanner.nextLine();
                                
                                if (userCommand.equals("1"))
                                {
                                    userResult = userStatement.executeQuery("select * from mysql.user;");  
                                    System.out.println("\nUsers in " + db + ": ");
                                    while(userResult.next())
                                    {
                                        System.out.println("  " + userResult.getString(2));
                                    } 
                                }
                                
                                if (userCommand.equals("2"))
                                {
                                    String userName = null, userPass = null;
                                    System.out.print("\n  Enter in new user name: ");
                                    userName = scanner.nextLine();
                                    System.out.print("  Enter in new user password: ");
                                    userPass = scanner.nextLine();
                                    
                                    userResult = userStatement.executeQuery("select user from mysql.user where user = '" + userName +"';"); 
                                 
                                    if (userResult.next() == true) System.out.println("  User " + userName + "already exist");
                                    else
                                    {
                                        userStatement.executeUpdate("CREATE USER '" + userName +"'@'localhost' IDENTIFIED BY '" + userPass +"';");
                                        System.out.println("User " + userName +  " created");
                                    }
                                }
                                
                                if (userCommand.equals("3"))
                                {
                                    String userName = null, userPass = null;
                                    System.out.print("\n  Enter in user name: ");
                                    userName = scanner.nextLine();

                                    
                                    userResult = userStatement.executeQuery("select user from mysql.user where user = '" + userName +"';"); 
                                 
                                    if (userResult.next() == false) System.out.println("  User " + userName + " does not exist");
                                    else
                                    {
                                        userStatement.executeUpdate("DROP USER '" + userName +"'@'localhost';");
                                        System.out.println("User " + userName +  " deleted");
                                    }
                                }
                                
                                if (userCommand.equals("4"))
                                {
                                    String userName = null;
                                    System.out.print("\n  Enter in user name: ");
                                    userName = scanner.nextLine();
  
                                    userResult = userStatement.executeQuery("select user from mysql.user where user = '" + userName +"';"); 
                                 
                                    if (userResult.next() == false) System.out.println("  User " + userName + " does not exist");
                                    else
                                    {
                                        userStatement.executeUpdate("DROP USER '" + userName +"'@'localhost';");
                                        System.out.println("User " + userName +  " deleted");
                                    }
                                }
                                
                            }while(!userCommand.equals("x"));
                            
                            
                        }
                        
                        System.out.println();
                    }while (!dbaCommand.equals("x"));     
                }
                
                if (command.equals("0")) System.out.println(commandList);

                if (command.equals("1"))
                {

                    String fstar, lstar;
                    String idnum;

                    System.out
                            .print("	Please enter the first name of the star (if applicable): ");
                    fstar = scanner.nextLine();
                    System.out
                            .print("	Please enter the last name of the star (if appplicable): ");
                    lstar = scanner.nextLine();
                    System.out
                            .print("	Please enter the id of the star (if appplicable): ");
                    idnum = scanner.nextLine();
                    ResultSet result = null;

                    if (!fstar.isEmpty() && !lstar.isEmpty())
                    {
                        Statement select = connection.createStatement();
                        result = select
                                .executeQuery("SELECT * FROM movies "
                                        + "INNER JOIN stars_in_movies ON movies.id = stars_in_movies.movie_id "
                                        + "INNER JOIN stars ON stars.id = stars_in_movies.star_id "
                                        + "WHERE stars.first_name = '" + fstar
                                        + "' " + "AND stars.last_name = '"
                                        + lstar + "';");
                    }

                    else if (fstar.isEmpty() && !lstar.isEmpty())
                    {
                        Statement select = connection.createStatement();
                        result = select
                                .executeQuery("SELECT * FROM movies "
                                        + "INNER JOIN stars_in_movies ON movies.id = stars_in_movies.movie_id "
                                        + "INNER JOIN stars ON stars.id = stars_in_movies.star_id "
                                        + "WHERE stars.last_name = '" + lstar
                                        + "';");
                    }

                    else if (!fstar.isEmpty() && lstar.isEmpty())
                    {
                        Statement select = connection.createStatement();
                        result = select
                                .executeQuery("SELECT * FROM movies "
                                        + "INNER JOIN stars_in_movies ON movies.id = stars_in_movies.movie_id "
                                        + "INNER JOIN stars ON stars.id = stars_in_movies.star_id "
                                        + "WHERE stars.first_name = '" + fstar
                                        + "';");
                    }

                    else
                    {
                        Statement select = connection.createStatement();
                        result = select
                                .executeQuery("SELECT * FROM movies "
                                        + "INNER JOIN stars_in_movies ON movies.id = stars_in_movies.movie_id "
                                        + "INNER JOIN stars ON stars.id = stars_in_movies.star_id "
                                        + "WHERE stars.id = '" + idnum + "';");
                    }

                    System.out.println();
                    ResultSetMetaData metadata = result.getMetaData();

                    // print table's contents, field by field
                    while (result.next())
                    {
                        System.out.println("Id = " + result.getInt(1));
                        System.out.println("Title = " + result.getString(2));
                        System.out.println("Year = " + result.getInt(3));
                        System.out.println("Director = " + result.getString(4));
                        System.out.println("Banner URL = "
                                + result.getString(5));
                        System.out.println("Trailer URL = "
                                + result.getString(6));
                        System.out.println();
                    }
                }

                if (command.equals("2"))
                {
                    ResultSet result;
                    System.out.print("	Enter the first name of the star: ");
                    String fname = scanner.nextLine();
                    System.out.print("	Enter the last name of the star: ");
                    String lname = scanner.nextLine();
                    if (lname.equals("") || fname.equals(""))
                    {
                        lname = fname;
                        fname = "";
                    }

                    Statement select = connection.createStatement();
                    select.executeUpdate("INSERT INTO stars (first_name, last_name) VALUES ('"
                            + fname + "', '" + lname + "');");
                }

                if (command.equals("3"))
                {
                    System.out.print("  Enter customer's first name: ");
                    String fcname = scanner.nextLine();
                    System.out.print("  Enter customer's last name: ");
                    String lcname = scanner.nextLine();

                    if (lcname.equals("") || fcname.equals(""))
                    {
                        lcname = fcname;
                        fcname = "";
                    }

                    System.out.print("  Enter customer's credit card number: ");
                    String ccnum = scanner.nextLine();
                    System.out.print("  Enter customer's address: ");
                    String addr = scanner.nextLine();
                    System.out.print("  Enter customer's email: ");
                    String email = scanner.nextLine();
                    System.out.print("  Enter customer's password: ");
                    String pass = scanner.nextLine();

                    Statement select = connection.createStatement();
                    select.executeUpdate("INSERT INTO customers (first_name, last_name, cc_id, address, email, password) VALUES ('"
                            + fcname
                            + "', '"
                            + lcname
                            + "', '"
                            + ccnum
                            + "', '"
                            + addr
                            + "', '"
                            + email
                            + "', '"
                            + password + "');");
                }

                if (command.equals("4"))
                {
                    System.out.print("  Enter customer's ID: ");
                    int custid = scanner.nextInt();

                    Statement select = connection.createStatement();
                    select.executeUpdate("DELETE FROM customers WHERE id = '"
                            + custid + "';");
                }

                if (command.equals("5"))
                {
                    Statement select = connection.createStatement();
                    ResultSet result = select
                            .executeQuery("Select * from movies");
                    ResultSetMetaData metadata = result.getMetaData();

                    System.out.println("movies table: ");
                    while (result.next())
                    {
                        System.out.println("ID: " + result.getInt(1));
                        System.out.println("Title: " + result.getString(2));
                        System.out.println("Year: " + result.getInt(3));
                        System.out.println("Director: " + result.getString(4));
                        System.out
                                .println("Banner URL: " + result.getString(5));
                        System.out.println("Trailer URL: "
                                + result.getString(6));
                        System.out.println();
                    }
                    System.out.println("-----------------------------------");

                    System.out.println("stars table: ");
                    select = connection.createStatement();
                    result = select.executeQuery("Select * from stars");
                    metadata = result.getMetaData();
                    while (result.next())
                    {
                        System.out.println("ID: " + result.getInt(1));
                        System.out
                                .println("First Name: " + result.getString(2));
                        System.out.println("Last Name: " + result.getString(3));
                        System.out.println("Date of Birth: "
                                + result.getString(4));
                        System.out.println("Photo URL: " + result.getString(5));
                        System.out.println();
                    }
                    System.out.println("-----------------------------------");

                    System.out.println("stars_in_movies table: ");
                    select = connection.createStatement();
                    result = select
                            .executeQuery("Select * from stars_in_movies");
                    metadata = result.getMetaData();
                    while (result.next())
                    {
                        System.out.println("Star ID: " + result.getInt(1));
                        System.out.println("Movie ID: " + result.getInt(2));
                        System.out.println();
                    }
                    System.out.println("-----------------------------------");

                    System.out.println("genres table: ");
                    select = connection.createStatement();
                    result = select.executeQuery("Select * from genres");
                    metadata = result.getMetaData();
                    while (result.next())
                    {
                        System.out.println("ID: " + result.getInt(1));
                        System.out.println("Name: " + result.getString(2));
                        System.out.println();
                    }
                    System.out.println("-----------------------------------");

                    System.out.println("genres_in_movies table: ");
                    select = connection.createStatement();
                    result = select
                            .executeQuery("Select * from genres_in_movies");
                    metadata = result.getMetaData();
                    while (result.next())
                    {
                        System.out.println("Genre ID: " + result.getInt(1));
                        System.out.println("Movie ID: " + result.getInt(2));
                        System.out.println();
                    }
                    System.out.println("-----------------------------------");

                    System.out.println("creditcards table: ");
                    select = connection.createStatement();
                    result = select.executeQuery("Select * from creditcards");
                    metadata = result.getMetaData();
                    while (result.next())
                    {
                        System.out.println("ID: " + result.getString(1));
                        System.out
                                .println("First Name: " + result.getString(2));
                        System.out.println("Last Name: " + result.getString(3));
                        System.out.println("Expiration Date: "
                                + result.getString(4));
                        System.out.println();
                    }
                    System.out.println("-----------------------------------");

                    System.out.println("customers table: ");
                    select = connection.createStatement();
                    result = select.executeQuery("Select * from customers");
                    metadata = result.getMetaData();
                    while (result.next())
                    {
                        System.out.println("ID: " + result.getInt(1));
                        System.out
                                .println("First Name: " + result.getString(2));
                        System.out.println("Last Name: " + result.getString(3));
                        System.out.println("Credit Card ID: "
                                + result.getString(4));
                        System.out.println("Address:  " + result.getString(5));
                        System.out.println("Email: " + result.getString(6));
                        System.out.println("Password: " + result.getString(7));
                        System.out.println();
                    }
                    System.out.println("-----------------------------------");

                    System.out.println("sales table: ");
                    select = connection.createStatement();
                    result = select.executeQuery("Select * from sales");
                    metadata = result.getMetaData();
                    while (result.next())
                    {
                        System.out.println("ID: " + result.getInt(1));
                        System.out.println("Customer ID: " + result.getInt(2));
                        System.out.println("Movie ID: " + result.getInt(3));
                        System.out.println("Sale Date: " + result.getString(4));
                        System.out.println();
                    }
                    System.out.println();
                }

                if (command.equals("6"))
                {
                    System.out.print("   Enter a valid SQL command: ");
                    String c = scanner.nextLine();
                    String[] scString = c.split(" ");

                    if (scString[0].toLowerCase().equals("select"))
                    {
                        Statement select = connection.createStatement();
                        ResultSet result = select.executeQuery(c);
                        ResultSetMetaData metadata = result.getMetaData();
                        int columnCount = metadata.getColumnCount();

                        while (result.next())
                        {
                            for (int i = 1; i < columnCount; i++)
                            {
                                System.out.println(result.getObject(i));
                            }
                            System.out.println();
                        }
                    }

                    else
                    {
                        Statement select = connection.createStatement();
                        select.executeUpdate(c);
                        System.out.println("The query has be executed.");
                    }
                }

            } while (!command.equals("q"));
            } 

            connection.close();

        } while (!username.equals("") && !password.equals(""));
    }

}
