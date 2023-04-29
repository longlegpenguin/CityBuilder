# Controller
Please read through.

## Initialization
+ A controller take in a game model for instantiation.  
```java
Controller c = new Controller(gm);
```

## API
+ The methods which are public to be used in view are as following. 

### 1
```java
public void mouseClickRequest(Coordinate coordinate, ICallBack callBack);
```
> For any click on the world grid, send this request to the game model.
> 
> The update is automatically synchronized through the implementation of
> icallback interface. i.e. The newly created instance of zone/facility.  
> The caller side is expected to implement the icallback
> interface, in case any new information is required by it.
> 
> The actual behaviour/modification is done according to the game mode,
> but nothing is required from the caller side.

### 2
```java
public void switchModeRequest(GameMode gameMode);
```
> This is to be called in order to change the game mode,
> which is used internally as described above.
> The available game modes: 
>  + SELECTION_MODE,
     RESIDENTIAL_MODE,
     INDUSTRIAL_MODE,
     COMMERCIAL_MODE,
     ROAD_MODE,
     POLICE_MODE,
     STADIUM_MODE,
     SCHOOL_MODE,
     UNIVERSITY_MODE,
     FOREST_MODE,
     UPGRADE_MODE,
     DEMOLISH_MODE

### 3
```java
public void regularUpdateRequest(int dayPass, ICallBack callBack);
```
> This is to be called by the timer, to update every internal 
> parallel change of the game in game model.
> 
> The call back is used, again to update changes to view.
> i.e. City statistics & world date.