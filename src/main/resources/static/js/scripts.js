class Chef{
    constructor(name,model,year){
        this.name=name;
        this.model=model;
        this.year=year;
    }
}

let arrRobots = new Array(10);

var r = new Chef('James','Italian','2017');
var r2 = new Chef('Peter','American','2019');
var r3 = new Chef('Steve','Indian','2021');
var r4 = new Chef('Jack','Middle-Eastern','2022');

arrRobots[0]=r;
arrRobots[1]=r2;
arrRobots[2]=r3;
arrRobots[3]=r4;

var selectedpara = document.getElementById("mypara");
console.log(selectedpara);
var e = document.createElement("p");
e.className ="h4";
e.setAttribute('style','color:blue');
e.innerText = "Some of our best chef for each category ";

selectedpara.appendChild(e);

for(let i=0;i<arrRobots.length; i++){
    var div = document.createElement('div');
    var paragraph = document.createElement('p');

    paragraph.innerText =`${arrRobots[i].name} ${arrRobots[i].model} ${arrRobots[i].year}`;
    paragraph.className='h6';
    div.className="text -bg -secondary rounded-3";

    div.appendChild(paragraph);
    selectedpara.appendChild(div);

}