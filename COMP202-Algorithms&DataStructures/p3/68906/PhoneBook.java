package code;

import java.util.List;

import given.ContactInfo;

/*
 * A phonebook class that should:
 * - Be efficiently (O(log n)) searchable by contact name
 * - Be efficiently (O(log n)) searchable by contact number
 * - Be searchable by e-mail (can be O(n)) 
 * 
 * The phonebook assumes that names and numbers will be unique
 * Extra exercise (not to be submitted): Think about how to remove this assumption
 * 
 * You need to use your own data structures to implement this
 * 
 * Hint: You need to implement a multi-map! 
 * 
 */
public class PhoneBook {

  /*
   * ADD MORE FIELDS IF NEEDED
   * 
   */

  public PhoneBook() {
    /*
     * TO BE IMPLEMENTED
     * 
     */
  }

  // Returns the number of contacts in the phone book
  public int size() {
    return 0;
  }

  // Returns true if the phone book is empty, false otherwise
  public boolean isEmpty() {
    return false;
  }

  //Adds a new contact or overwrites an existing contact with the given info
  // Args should be given in the order of e-mail and address which is handled for
  // you
  // Note that it can also be empty. If you do not want to update a field pass
  // null
  public void addContact(String name, String number, String... args) {
    int numArgs = args.length;
    String email = null;
    String address = null;

    /*
     * Add stuff here if needed
     */

    if (numArgs > 0)
      if (args[0] != null)
        email = args[0];
    if (numArgs > 1)
      if (args[1] != null)
        address = args[1];
    if (numArgs > 2)
      System.out.println("Ignoring extra arguments");

    /*
     * TO BE IMPLEMENTED
     * 
     */
  }

  // Return the contact info with the given name
  // Return null if it does not exists
  // Should be O(log n)!
  public ContactInfo searchByName(String name) {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return null;
  }

  // Return the contact info with the given phone number
  // Return null if it does not exists
  // Should be O(log n)!
  public ContactInfo searchByPhone(String phoneNumber) {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return null;

  }

  // Return the contact info with the given e-mail
  // Return null if it does not exists
  // Can be O(n)
  public ContactInfo searchByEmail(String email) {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return null;

  }

  // Removes the contact with the given name
  // Returns true if there is a contact with the given name to delete, false otherwise
  public boolean removeByName(String name) {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return false;
  }

  // Removes the contact with the given name
  // Returns true if there is a contact with the given number to delete, false otherwise
  public boolean removeByNumber(String phoneNumber) {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return false;
  }

  // Returns the number associated with the name
  public String getNumber(String name) {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return null;
  }

  // Returns the name associated with the number
  public String getName(String number) {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return null;
  }
  
  // Update the email of the contact with the given name
  // Returns true if there is a contact with the given name to update, false otherwise
  public boolean updateEmail(String name, String email) {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return false;
  }
  
  // Update the address of the contact with the given name
  // Returns true if there is a contact with the given name to update, false otherwise
  public boolean updateAddress(String name, String address) {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return false;
  }

  // Returns a list containing the contacts in sorted order by name
  public List<ContactInfo> getContacts() {
    /*
     * TO BE IMPLEMENTED
     * 
     */
    return null;
  }

  // Prints the contacts in sorted order by name
  public void printContacts() {
    /*
     * TO BE IMPLEMENTED
     * 
     */
  }
}
