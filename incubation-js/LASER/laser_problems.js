//laser.js - JavaScript for storing problems for the LASER puzzle suite.
//
// 2014.09.22 yybtcbk split from main html

//*************************************
//************* Problems  *************
//*************************************
function setLaserProbs(){ //to be called from load() in laser.js
	var lp; //for temp storage of each LASER problem; call new each iteration
	
	//===== Initial Problem
	lp = new LaserProblem();
	lp.id = "ex001";
	lp.text = "Default Problem";
	lp.author = "yybtcbk";
	lp.content = ".3..........1......\n" + 
	             "...................\n" + 
	             "...................\n" + 
	             "...................\n" + 
	             "...............1..2\n" + 
	             "...................\n" + 
	             ".........2.....2...\n" + 
	             "...................\n" + 
	             "...................\n" + 
	             "...................\n" + 
	             "...................\n" + 
	             ".....4.............\n" + 
	             "...................";
	lp.answer = "oxoxoxoxxxxxxxxxxox/xoxxxoxoxxoxo";
	laserProbsArr.push(lp);
	
	
	//-------divider-------
	lp = new LaserProblem();
	lp.id = "divider";
	lp.text = "-------------------- v EASY v --------------------";
	lp.content = "0";
	laserProbsArr.push(lp);
	
	lp = new LaserProblem();
	lp.id = "ea001";
	lp.text = "One Laser";
	lp.author = "[example]";
	lp.content = "..1";
	lp.answer = "xox/x";
	laserProbsArr.push(lp);
	
	lp = new LaserProblem();
	lp.id = "ea002";
	lp.text = "Leave No Laser Indeterminate";
	lp.author = "[example]";
	lp.content = "...\n" +
			         "3..\n" +
			         "...";
	lp.answer = "xox/oxo";
	laserProbsArr.push(lp);
	
	lp = new LaserProblem();
	lp.id = "ea003";
	lp.text = "Turn Off the Lights";
	lp.author = "[example]";
	lp.content = "..1..\n" +
			         ".....\n" +
			         ".1.1.";
	lp.answer = "xxxxx/xox";
	laserProbsArr.push(lp);
	
	
	//-------divider-------
	lp = new LaserProblem();
	lp.id = "divider";
	lp.text = "------------------ v NORMAL v ------------------";
	laserProbsArr.push(lp);
	
	lp = new LaserProblem();
	lp.id = "no001";
	lp.text = "Proof by Contradiction";
	lp.author = "yybtcbk";
	lp.content = ".1...\n" + 
	             ".....\n" + 
	             ".1..2\n" + 
	             ".....";
	lp.answer = "xxxox/xoxx";
	laserProbsArr.push(lp);
	
	
	//-------divider-------
	lp = new LaserProblem();
	lp.id = "divider";
	lp.text = "-------------------- v HARD v --------------------";
	laserProbsArr.push(lp);
	
	lp = new LaserProblem();
	lp.id = "ha001";
	lp.text = "Proof by Contradiction 2";
	lp.author = "yybtcbk";
	lp.content = "....................\n" + 
	             "...2.........11..2..\n" + 
	             ".............11..2.1\n" + 
	             "....................\n" + 
	             ".......1..3.........\n" + 
	             "....................\n" + 
	             "....................\n" + 
	             "1...................\n" + 
	             "1................1..\n" + 
	             "....................\n" + 
	             "....................\n" + 
	             ".......1............";
	lp.answer = "xoxxoxxxxoxoxxxxoxxx/oxxoxxxxxxox";
	laserProbsArr.push(lp);
	
}

