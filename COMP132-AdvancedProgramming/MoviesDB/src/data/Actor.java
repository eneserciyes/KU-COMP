package data;

public class Actor {
    private String name, surname;
    private int code;
    private int id;
    public Actor(String surname, String name) {
        this.surname = surname;
        this.name = name;


        //Hash code is generated to be able to use equals method.
        //Best way to do it is relating hashcode to name and surname of the actor.
        code= 0;

        for(char c: name.toLowerCase().toCharArray()){
            code += c;
        }
        for(char c: surname.toLowerCase().toCharArray()){
            code += c;
        }

    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id =id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        if(surname.length()>0)
            return String.format("%s %s",name.substring(0,1).toUpperCase()+name.substring(1),
                surname.substring(0,1).toUpperCase()+surname.substring(1));
        else
        {
            try{
                return String.format("%s%s",name.substring(0,1).toUpperCase(),name.substring(1));
            }catch (StringIndexOutOfBoundsException e){
                System.out.println(name);
            }
        }
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(this.getClass()!=obj.getClass())
            return false;
        return (this.surname.toLowerCase().equals(((Actor) obj).getSurname().toLowerCase())
                && this.name.toLowerCase().equals(((Actor) obj).getName().toLowerCase()));
    }

    @Override
    public int hashCode() {
        return this.code;
    }
}
