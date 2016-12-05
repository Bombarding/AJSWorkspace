import java.util.Scanner;

public class Encryption 
{
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	String message;
	String encrypted;
	int len;
	char index;


public void encrypt(){
	len = message.length();
	
	for(int i = 0; i < len; i++){
	char temp = ' ';
	temp = message.charAt(i);
	index += temp;
	}
		
	for(int k = 0; k < 23; k++){
		char temp2 = ' ';
		if(temp2 == alphabet[k]){
		temp2 = alphabet[k+3];
		}
		index += temp2;
		}
	/*for(int l = 23; l <= 26; l++){
		char temp3 = ' ';
		if(index == alphabet[l]){
			temp3 = alphabet[l-23];
			}
		index += temp3;
		}	*/
			
}



	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the String for Encryption");
		String message = new String();
		message = scan.next();
		

	}

}

/*
import java.util.Scanner;

public class Encryption 
{
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	
	public static String encrypt(String plainText, int shiftKey)
	{
		plainText = plainText.toLowerCase();
		String cipherText = "";
		for(int i=0; i< plainText.length(); i++)
		{
			int characterPosition = ALPHABET.indexOf(plainText.charAt(i));
			int keyValue = (shiftKey + characterPosition) % 26;
			char replaceValue = ALPHABET.charAt(keyValue);
			cipherText += replaceValue;
		}
		return cipherText;
	}
	public static String decrypt(String cipherText, int shiftKey)
	{
		cipherText = cipherText.toLowerCase();
		String plainText = "";
		for(int i=0; i<cipherText.length(); i++)
		{
			int characterPosition = ALPHABET.indexOf(cipherText.charAt(i));
			int keyValue = (characterPosition - shiftKey) % 26;
			if(keyValue < 0)
			{
				keyValue = ALPHABET.length() + keyValue;
			}
			char replaceValue = ALPHABET.charAt(keyValue);
			plainText += replaceValue;
		}
		return plainText;
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the String for Encryption");
		String message = new String();
		message = scan.next();
		System.out.println(encrypt(message, __));
		System.out.println(decrypt(encrypt(message, __), __));
		scan.close();

	}

}
*/
