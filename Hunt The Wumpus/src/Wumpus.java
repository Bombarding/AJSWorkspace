//Chris Johnson
//Shaun Mbateng
//Hunt the Wumpus
//CLASSPATH=$CLASSPATH:.
import java.io.*;
import java.util.*;


class HunttheWumpus
{
	public static void main(String [] args) throws IOException
	{
		int again=1;
		while(again==1)
		{
			Scanner gamefile = new Scanner(new FileReader("Wumpus.txt"));
			int cavenumber= gamefile.nextInt();
			//int spidernumber= gamefile.nextInt();
			//int botomlesspitnumber= gamefile.nextInt();
			Caves [] room;
			room= new Caves[cavenumber+1];
			// Reads into the array the information for each room
			for(int i=1; i<cavenumber+1; i++) {room[i]= new Caves(gamefile);}
		
			System.out.println("");
			System.out.println("***Welcome to Hunt the Wumpus!***");
			int roomplace= 1;
			
			//Place the wumpus into a room
			int A=0; int wumpus=0;
			while(A<cavenumber)
				{
					int B=(int)(1+(cavenumber)*Math.random());
					if(B!=roomplace){wumpus=B; break;}
					else A++;
				}
			//Place a pit
			int C=0; int pit=0;
			while(C<cavenumber)
				{
					int D=(int)(1+(cavenumber)*Math.random());
					if((D!=roomplace)&&(D!=wumpus)){pit=D; break;}
					else C++;
				}
			//Place a spider
			int E=0; int spider=0;
			while(E<cavenumber)
				{
					int F=(int)(1+(cavenumber)*Math.random());
					if((F!=roomplace)&&(F!=wumpus)&&(F!=pit)){spider=F; break;}
					else E++;
				}
			//Place a supply of arrows in a room
			int G=0; int supplyarrows=0;
			while(G<cavenumber)
				{
					int H=(int)(1+(cavenumber)*Math.random());
					if((H!=roomplace)&&(H!=wumpus)&&(H!=pit)&&(H!=spider)){supplyarrows=H; break;}
					else G++;
				}
		
			//Place bats in a room
			int I=0; int bats=0;
			while(I<cavenumber)
				{
					int J=(int)(1+(cavenumber)*Math.random());
					if((J!=roomplace)&&(J!=wumpus)&&(J!=pit)&&(J!=spider)&&(J!=supplyarrows)){bats=J; break;}
					else G++;
				}
		
			int K=0;
			int arrows=3;
			while(K==0) Sayid:
			{	
				//Prints out the current room and the description for the room.
				System.out.println("You are in room "+roomplace);
				System.out.println("You have "+arrows+" arrows left");
				System.out.println(room[roomplace].getWarning());
				//Prints where the exits are.
				System.out.println("There are doors to rooms " +room[roomplace].getExit1() + ","
				+room[roomplace].getExit2() + ", and " +room[roomplace].getExit3());
				//Prints out a phrase if there is a special room that a player can reach that has a wumpus or etc.
				if((wumpus==room[roomplace].getExit1()) || (wumpus==room[roomplace].getExit2())
					|| (wumpus==room[roomplace].getExit3())) {System.out.println("You smell some nasty Wumpus!");}
				if((spider==room[roomplace].getExit1()) || (spider==room[roomplace].getExit2())
					|| (spider==room[roomplace].getExit3())) {System.out.println("You hear a faint clicking noise.");}
				if((pit==room[roomplace].getExit1()) || (pit==room[roomplace].getExit2())
					|| (pit==room[roomplace].getExit3())) {System.out.println("You smell a dank odor.");}
				else{}
				// Have the user move or shoot.
				System.out.println("(M)ove or (S)hoot?");
				Scanner cin = new Scanner(System.in);
				String choice=cin.next();
				System.out.println("");
				int L;
				if((choice.equalsIgnoreCase("M")) || (choice.equalsIgnoreCase("S")))
					{
						System.out.println("Which room?");
						L=cin.nextInt();
						System.out.println("");
						if((L==room[roomplace].getExit1()) || (L==room[roomplace].getExit2()) || 
						(L==room[roomplace].getExit3()))
						{
							// If the player chooses to move.
							if(choice.equalsIgnoreCase("M"))
							{
								//If where they move to has a special in the room.
								if(L==wumpus)
								{
									System.out.println("OH NO!!! the wumpus got you!");
									System.out.println(" ** You Lose! **");
									K=1;
								}
								else if(L==spider)
								{
									System.out.println("Gross there are giant spiders everywhere! you quickly flee the cave with your life.");
									System.out.println(" ** You Lose! ** ");
									K=1;
								}
								else if(L==pit)
								{
									System.out.println("As you enter the next room you fall down into a bottomless pit.");
									System.out.println(" ** You Lose! ** ");
									K=1;
								}
								else if(L==supplyarrows)
								{
									System.out.println("You have stumbled upon a supply cache of arrows!");
									arrows=3;
									roomplace=L;
									break Sayid;
								}
								else if(L==bats)
								{
									System.out.println("SUPRISE BAT ATTACK! they transport you to a new room.");
									roomplace=(int)(1+(cavenumber)*Math.random());
										//If placed in a room with the wumpus or another surpise.
										if((roomplace==wumpus)||(roomplace==pit)||(roomplace==spider)||(roomplace==supplyarrows))
										{
											if(roomplace==wumpus)
											{
												System.out.println("OH NO!!! the wumpus got you!");
												System.out.println(" ** You Lose! **");
												K=1;
											}
											else if(roomplace==spider)
											{
												System.out.println("Gross there are giant spiders everywhere! you quickly flee the cave with your life.");
												System.out.println(" ** You Lose! ** ");
												K=1;
											}
											else if(L==pit)
											{
												System.out.println("As you enter the next room you fall down into a bottomless pit.");
												System.out.println(" ** You Lose! ** ");
												K=1;
											}
											else if(L==supplyarrows)
											{
												System.out.println("You have stumbled upon a supply cache of arrows!");
												arrows=3;
												break Sayid;
											}
											
										}
										else {break Sayid;}
								}
								//If no special in the room you are placed in
								else
								{
									roomplace=L;
								}
							}
							//If you chose to shoot.
							else if(choice.equalsIgnoreCase("S"))
							{	
								//If you have no arrows and try to shoot.
								if(arrows==0)
								{
									System.out.println("Sorry you are out of arrows and can not shoot.");
									break Sayid;
								}
								//If you do have arrows and shoot
								else
								{
									arrows--;
									//Chooses correct room.
									if(L==wumpus)
									{
										System.out.println("Your arrow goes down the tunnel and finds its mark!");
										System.out.println("You shot the Wumpus!  ** You Win! **");
										K=1;
									}
									//Chooses wrong room.
									else
									{
										System.out.println("Your arrow goes down the tunnel and is lost.  You missed.");
										break Sayid;
									}
								}
							}
							
							
						}
						//If you select a room that is not on the list of available rooms to move to or shoot
						else
						{
							System.out.println("Dimwit!  You can't get to there from here.");
						}		
					}
				//If you select something else other than move or shoot	
				else
					{
						System.out.println("That is not a choice, please choose M or S.");
					}
			}
			//This is for if a player wishes to play again.
			int M=0;
			while(M==0) Rose:
			{
				Scanner cin = new Scanner(System.in);
				System.out.println("Would you like to play again? (Y)es or (N)o?");
				String Answer=cin.next();
				if((Answer.equalsIgnoreCase("Y")) || (Answer.equalsIgnoreCase("N")))
				{
					if (Answer.equalsIgnoreCase("Y"))
					{
						again=1;
						M=1;
						System.out.println("");
					}
					else if(Answer.equalsIgnoreCase("N"))
					{
						System.out.println("Have a great day!");
						System.out.println("");
						M=1;
						again=0;
					}
				}
				else
				{
					System.out.println("Sorry that is not a choice please choose Y or N.");
					break Rose;
					
				}
			
			
			}
		}
	}
}

/*

*/


