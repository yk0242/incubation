;jsdefs.ks - defines all js class defs
; to be called from make.ks and game start
;　
; apparently TyranoScript doesn't save prototyped functions within save datas, so
; workaround by re-creating each class object in make.ks, and copying data

*start

[iscript]
//======= Class def for single room =======
var Room = function(){
	this.type = "empty";
	this.name = "noName";
	this.doors = [0,0,0,0]; //N,E,S,W
	
};
//use this to copy old Room data into newly created Room
Room.prototype.copy = function(aRoom){
	this.type = aRoom.type;
	this.name = aRoom.name;
	this.doors = aRoom.doors;
};

Room.prototype.rename = function(aName){
	this.name = aName;
};
Room.prototype.changeType = function(aType){
	this.type = aType;
};
Room.prototype.addDoor = function(aDir){//aDir:0=N,1=E,2=S,3=W
	if(aDir<0 || aDir>3 ) return;
	this.doors[aDir] = 1;
	//TODO add door coming back else this is a trap door...
};

//======= Class def for single floor =======
var Floor = function(aFloornum,aNsSize,aEwSize){
	this.fnum = aFloornum;
	this.nsSize = aNsSize;
	this.ewSize = aEwSize;
	this.rooms = [];
	for(var i=0; i<this.nsSize; i++){
		this.rooms[i] = new Array(this.ewSize);
	}
};
//use this to copy old Floor data into newly created Floor
Floor.prototype.copy = function(aFloor){
	this.fnum = aFloor.fnum;
	this.nsSize = aFloor.nsSize;
	this.ewSize = aFloor.ewSize;
	this.rooms = [];
//	this.rooms = aFloor.rooms;
	for(var i=0; i<this.nsSize; i++){
		this.rooms[i] = [];
		for(var j=0; j<this.ewSize; j++){
			if(aFloor.rooms[i][j]===null){
				this.rooms[i][j] = null;
				continue;
			}
			var rm = new Room();
			rm.copy(aFloor.rooms[i][j]);
			this.rooms[i][j] = rm;
		}
	}
};

Floor.prototype.addRoom = function(aS,aE){
	this.rooms[aS][aE] = new Room();
};
Floor.prototype.extendNorth = function(){};
Floor.prototype.extendSouth = function(){};
Floor.prototype.extendEast = function(){};
Floor.prototype.extendWest = function(){};

//======= Class def for building =======
var Building = function(){
	this.name = "Building";
	this.nsSize = 5;   //start with a 
	this.ewSize = 5;   // 5x5 building
	this.heightAbove = 1;
	this.depthBelow = 0;
	this.floorsAbove = [];
	this.floorsBelow = [];
	var groundFloor = new Floor(0,this.nsSize,this.ewSize);
	var tS = Math.floor(this.nsSize/2);
	var tE = Math.floor(this.ewSize/2);
	groundFloor.addRoom(tS,tE);
	groundFloor.rooms[tS][tE].rename("Base Room");
	groundFloor.rooms[tS][tE].changeType("base-room");
	this.floorsAbove.push(groundFloor);

};
//use this to copy old Building data into newly created Building
Building.prototype.copy = function(aBuilding){
	this.name = aBuilding.name;
	this.nsSize = aBuilding.nsSize;
	this.ewSize = aBuilding.ewSize;
	this.heightAbove = aBuilding.heightAbove;
	this.depthBelow = aBuilding.depthBelow;
	this.floorsAbove = [];
	this.floorsBelow = [];
//	this.floorsAbove = aBuilding.floorsAbove;
//	this.floorsBelow = aBuilding.floorsBelow;
	for(var i=0; i<this.heightAbove; i++){
		var fl = new Floor(i,this.nsSize,this.ewSize);
		fl.copy(aBuilding.floorsAbove[i]);
		this.floorsAbove[i] = fl;
	}
	for(var i=0; i<this.depthBelow; i++){
		var fl = new Floor(-i-1,this.nsSize,this.ewSize);
		fl.copy(aBuilding.floorsBelow[i]);
		this.floorsBelow[i] = fl;
	}
};

Building.prototype.extendUp = function(){
	var newFloor = new Floor(this.heightAbove++,this.nsSize,this.ewSize);
	this.floorsAbove.push(newFloor);
};
Building.prototype.extendDown = function(){
	var newFloor = new Floor(-(++this.depthBelow),this.nsSize,this.ewSize);
	this.floorsBelow.push(newFloor);
};
Building.prototype.extendNorth = function(){};
Building.prototype.extendSouth = function(){};
Building.prototype.extendEast = function(){};
Building.prototype.extendWest = function(){};
Building.prototype.rename = function(aName){
	this.name = aName;
};
Building.prototype.getRoom = function(aX,aY,aZ){
	if(f.z<0) return this.floorsBelow[-aZ-1].rooms[aY][aX];
	else      return this.floorsAbove[aZ].rooms[aY][aX];
};


//apparently scope seems to be isolated amongst iscript tags, so workaround by storing each in sf
sf.Room = Room;
sf.Floor = Floor;
sf.Building = Building;

[endscript]

[return]
