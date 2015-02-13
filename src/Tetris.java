class Tetris extends javax.swing.JPanel implements java.awt.event.KeyListener
{
	int[][] exist = new int[10][20]; 
	int score = 0;  
    javax.swing.JLabel scoreLabel = new javax.swing.JLabel("SCORE : 0");
    
    static int[][][] Rotx = { 
    	{ {0,0,1,2}, {0,0,0,1}, {2,0,1,2}, {0,1,1,1} },  
        { {0,0,1,1}, {1,2,0,1}, {0,0,1,1}, {1,2,0,1} },  
        { {1,1,0,0}, {0,1,1,2}, {1,1,0,0}, {0,1,1,2} },  
        { {0,1,2,2}, {0,1,0,0}, {0,0,1,2}, {1,1,0,1} },  
        { {1,0,1,2}, {1,0,1,1}, {0,1,1,2}, {0,0,1,0} },  
        { {0,1,0,1}, {0,1,0,1}, {0,1,0,1}, {0,1,0,1} },  
        { {0,1,2,3}, {0,0,0,0}, {0,1,2,3}, {0,0,0,0} }
    };
    static int[][][] Roty = {
    	{ {0,1,0,0}, {0,1,2,2}, {0,1,1,1}, {0,0,1,2} },  
    	{ {0,1,1,2}, {0,0,1,1}, {0,1,1,2}, {0,0,1,1} },  
    	{ {0,1,1,2}, {0,0,1,1}, {0,1,1,2}, {0,0,1,1} },  
       	{ {0,0,0,1}, {0,0,1,2}, {0,1,1,1}, {0,1,2,2} },  
       	{ {0,1,1,1}, {0,1,1,2}, {0,0,1,0}, {0,1,1,2} },  
       	{ {0,0,1,1}, {0,0,1,1}, {0,0,1,1}, {0,0,1,1} },  
       	{ {0,0,0,0}, {0,1,2,3}, {0,0,0,0}, {0,1,2,3} }   
    };
  
	public void addSquare(int x,int y)
	{
		exist[x][y] = 1;
	}
	
	public void removeSquare(int x,int y)
	{
	    exist[x][y] = 0;
	}
	 
	public void addTetriminos(int x, int y, int[] Posx, int[] Posy)
	{
		for (int i=0;i<4;i++)
	    {
			addSquare(x+Posx[i],y+Posy[i]);
	    }   
	}
	
	public void removeTetriminos(int x, int y, int[] Posx, int[] Posy)
	{
		for (int i=0;i<4;i++)
	    {
	    	removeSquare(x+Posx[i],y+Posy[i]);
	    }
	}
	
	public void paint(java.awt.Graphics pt)
	{
		super.paint(pt);
		for (int x=0;x<10;x++)
			for (int y=0;y<20;y++)
				if (exist[x][y]==1)
		        {
					pt.setColor(java.awt.Color.black);
					pt.fillRect(x*32,y*32,32,32);
					pt.setColor(java.awt.Color.blue);
					pt.fillRect(x*32+1,y*32+1,30,30);
		        }
		        else
		        {
		        	pt.setColor(java.awt.Color.white);
		        	pt.fillRect(x*32,y*32,32,32);
		        }	
	}
	
	public boolean checkPosition(int x,int y, int tetriminosId, int rotId)
	{
		int[] Posx = Rotx[tetriminosId][rotId];
	    int[] Posy = Roty[tetriminosId][rotId];
	     
	    for (int i=0;i<4;i++)  
	    {
	    	int PosSquarex = x+Posx[i];
	    	int PosSquarey = y+Posy[i];
	 
	    	if (PosSquarex<0) return false;
	    	if (PosSquarex>=10) return false;
	    	if (PosSquarey<0) return false;
	    	if (PosSquarey>=20) return false;
	 
	    	if (exist[PosSquarex][PosSquarey]==1) return false;
	    }
	    return true;
	}
	
	public void randomTetriminosTest()
	{
		try { Thread.sleep(1000); } catch (Exception ignore) {}
		int x,y,tetriminosId,rotId;
		
		while (true)  
	    {
			x=(int) (10*Math.random());    
		    y=(int) (20*Math.random());    
		    tetriminosId = (int) (7*Math.random());
		    rotId = (int) (4*Math.random());
		 
		    if (checkPosition(x,y,tetriminosId,rotId)) break;
	    }
		int[] Posx = Rotx[tetriminosId][rotId];
	    int[] Posy = Roty[tetriminosId][rotId];
	 
	    addTetriminos(x,y,Posx,Posy);
	    repaint();
	}
	
	boolean gameOver=false;
	public void tetriminosMove()
	{
	    int x=5,y=0;
	    int tetriminosId, rotId;
	 
	    tetriminosId = (int) (7*Math.random());
	    rotId = (int) (4*Math.random());

	    int[] Posx = Rotx[tetriminosId][rotId];
	    int[] Posy = Roty[tetriminosId][rotId];
	    
	    if (!checkPosition(x,y,tetriminosId,rotId))
	    {
	    	gameOver=true;
	    	addTetriminos(x,y,Posx,Posy);
	    	repaint();
	    	return;
	    }
	    
	    addTetriminos(x,y,Posx,Posy);
	    repaint();
	 
	    int delay=100; 
	    int frame=0;
	    boolean reachFloor=false;
	    
	    while (!reachFloor)
	    {
	    	try { Thread.sleep(delay); } catch (Exception ignore) {}
	    	removeTetriminos(x,y,Posx,Posy);
	      
	    	if (leftPressed && checkPosition(x-1,y,tetriminosId,rotId)) x -= 1;
	    	if (rightPressed && checkPosition(x+1,y,tetriminosId,rotId)) x += 1;
	    	if (downPressed && checkPosition(x,y+1,tetriminosId,rotId)) y += 1;
	    	if (spacePressed && checkPosition(x,y,tetriminosId,(rotId+1)%4))
	    	{
	    		rotId = (rotId+1)%4;
	    		Posx = Rotx[tetriminosId][rotId];
	    		Posy = Roty[tetriminosId][rotId];
	    		spacePressed=false; 
	    	}
	 
	    	if (frame % 30==0) y += 1;  
	    	if (!checkPosition(x,y,tetriminosId,rotId)) 
	      	{
	    		reachFloor=true;
	    		y -= 1;  
	      	}
	    	addTetriminos(x,y,Posx,Posy);
	    	repaint();
	    	frame++;
	    }
	}	
	
	public void clearLine(int[] full)
	{
	    for (int c=0;c<5;c++) 
	    {
	    	for (int i=0;i<full.length;i++)
	    	{
	    		if (full[i]==1)
	    		{
	    			for (int x=0;x<10;x++)
	    			{
	    				exist[x][i]=1-exist[x][i];
	    			}
	    		}
	    	}
	    	repaint();
	    	try { Thread.sleep(150); } catch (Exception ignore) {}
	    }
	}
	 
	public void adjustement(int[] full)
	{
		for (int line=0;line<full.length;line++)
	    {
	     	if (full[line]==1)
	     	{
	     		for (int y=line;y>=1;y--)
	     		{
	     			for (int x=0;x<10;x++)
	     			{
	     				exist[x][y] = exist[x][y-1];
	     			}
	     		}
	     	}
	    }
	}
	
	public void checkFullLine()
	{
		int[] completeLine = new int[20];
		
	    for (int y=0;y<20;y++)  
	    {
	    	int filledSquare = 0;
	    	for (int x=0;x<10;x++)  
	    	{
	    		if (exist[x][y]==1) filledSquare++;
	    		if (filledSquare==10) 
	    		{
	    			completeLine[y]=1;
	    		}
	    	}
	    }
	    clearLine(completeLine);
	    adjustement(completeLine);
	    score(completeLine);   
	}
	
	void score(int[] completeLine)
	{
	    int bonus=10;  
	    
	    for (int line=0;line<completeLine.length;line++)
	    {
	    	if (completeLine[line]==1)
	    	{
	    		score+=bonus;
	    	}
	    }
	    scoreLabel.setText("SCORE : "+score);
	}
	
	public void gameOver()
	{
		javax.swing.JFrame window2 = new javax.swing.JFrame("145866_Tetris Game Over"); 
		window2.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); 
		Tetris gameOver = new Tetris();
		gameOver.windowParameter2();
		window2.add(gameOver);
		window2.pack();
    	window2.setVisible(true);
	}
	  
	boolean leftPressed=false;
	boolean rightPressed=false;
	boolean downPressed=false;
	boolean spacePressed=false;
	
	public void keyPressed(java.awt.event.KeyEvent event)
	{
		if (event.getKeyCode()==37) 
	    {
			leftPressed=true;
	    }
	    if (event.getKeyCode()==39) 
	    {
	    	rightPressed=true;
	    }
	    if (event.getKeyCode()==40) 
	    {
	    	downPressed=true;
	    }
	    if (event.getKeyCode()==32) 
	    {
	    	spacePressed=true;
	    } 
	}
	
	public void keyReleased(java.awt.event.KeyEvent event)
	{
	    if (event.getKeyCode()==37) 
	    {
	    	leftPressed=false;
	    }
	    if (event.getKeyCode()==39) 
	    {
	    	rightPressed=false;
	    }
	    if (event.getKeyCode()==40) 
	    {
	    	downPressed=false;
	    }
	    if (event.getKeyCode()==32) 
	    {
	    	spacePressed=false;
	    }
	}
	
	public void keyTyped(java.awt.event.KeyEvent event)
	{
		
	}
	
	public void windowParameter() 
	{
		this.setPreferredSize(new java.awt.Dimension(440,640)); 
		this.setBackground(java.awt.Color.lightGray); 
		this.setLayout(null);   
		scoreLabel.setBounds(360,40,80,20);  
		this.add(scoreLabel);
	}
	
	public void windowParameter2() 
	{
		this.setPreferredSize(new java.awt.Dimension(400,200)); 
		this.setBackground(java.awt.Color.white); 
		this.setLayout(null);    
		javax.swing.JLabel gameOverLabel = new javax.swing.JLabel("Game Over");
		gameOverLabel.setBounds(10,10,10,10);  
		this.add(gameOverLabel);
	}
	
	public static void main(String[] args) throws Exception
	{
		javax.swing.JFrame window = new javax.swing.JFrame("145866_Tetris"); 
		window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		Tetris tetris = new Tetris();
		tetris.windowParameter();
		window.add(tetris);
    	window.pack();
    	window.setVisible(true);
    	
    	try { Thread.sleep(1000); } catch (Exception ignore) {}
    	
    	window.addKeyListener(tetris);  
        tetris.gameOver=false;
        while (!tetris.gameOver)
        {
        	tetris.tetriminosMove();
        	tetris.checkFullLine();
        }
        tetris.gameOver();
    } 
}
