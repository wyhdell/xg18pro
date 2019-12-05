import javax.servlet.http.HttpServletRequest;

public class Helper {
    public static int getIdFromRequest(HttpServletRequest request){
        return getIdFromRequest(request,"id");
    }
    public static int getIdFromRequest(HttpServletRequest request,String fieldName){
        String fieldPara = request.getParameter(fieldName);
        return Integer.parseInt(fieldPara);
    }

    public static String quote(String str){
        return "\"" + str + "\"";
    }

    public static void main(String[] args) {
        System.out.println(Helper.quote("schools:"));
    }
}
