
public class Token
{
  public String lexeme;
  public int type;

  public Token(String lex, int t) { lexeme=lex.toUpperCase(); type=t; }

  public String toString()
  {
    return "Token: "+lexeme+"\tType: "+typename[type];
  }

  public static final int INT=0;
  public static final int REAL=1;
  public static final int OP=2;
  public static final int ASSIGN=3;
  public static final int SEMI=4;
  public static final int VAR=5;
  public static final int QUIT=6;

  public static final String [] typename={"Integer","Real Number", "Operator","Assignment","Semicolon","Variable","Quit"};
}

