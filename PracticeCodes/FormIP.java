import java.util.ArrayList;
import java.util.List;


public class FormIP {
	
	public static boolean isValid(String s){
		if(s.length() == 3 && (Integer.parseInt(s) > 255 || Integer.parseInt(s) == 0))
			return false;
		if(s.length() == 3 && s.charAt(0) == '0')
			return false;
		if(s.length() == 2 && Integer.parseInt(s) == 0)
			return false;
		if(s.length() == 2 && s.charAt(0) == '0')
			return false;
		return true;
	}
	
	public static void createIP(String s, String ip, List<String>result, int limit){
		if(limit == 0){
			if(s.isEmpty())
				result.add(ip);
			return;
		}
		else{
			for(int i = 1; i <= 3; i++){
				if(s.length() >= i && isValid(s.substring(0, i))){
					if(limit == 1){
						createIP(s.substring(i), ip + s.substring(0, i), result, limit - 1);
					}
					else{
						createIP(s.substring(i), ip + s.substring(0, i) + ".", result, limit - 1);
					}
				}
			}
		}
	}
	
	public static List<String> restoreIPAddress(String s){
		List<String> result = new ArrayList<String>();
		
		createIP(s, "", result, 4);
		return result;
	}
	
	public static void main(String args[]){
		List<String> output = FormIP.restoreIPAddress("0000");
		for(String s: output){
			System.out.println(s);
		}
	}
}
