import java.lang.*;

public class trReply{
    public String body;
    public String usrn;
    Date dop;
    public trReply(String txt, String usr, int dt){
        dop = new Date(dt * 1000);
        body = txt;
        usrn = usr;

    }

    public Date rtrnDate(){
        return dop;
    }
    public String rtrnUsrn(){
        return usrn;

    }
    public String trnTxt(){
        return body;
    }
    public boolean editBody(String newTxt){
        try{
            body = editBody;
            
        }
        catch(exception e){
            return false;
        }
        return true;
    }
    
}