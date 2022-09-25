var items=document.getElementsByClassName('b');//图片

var index=0;//index表示第几张图片在展示---》第index张图片有active这个类名

var clearActive=function(){
    for(var i=0;i<items.length;i++){
        items[i].className="item b";

    }

}

var goIndex=function(){
    clearActive();
    items[index].className='b active c imageSize ';
}

var goNext=function(){
    if(index<4){
        index ++;
    }else{
        index=0;
    }
    goIndex();
}

var goPre=function(){
    if(index==0){
        index=4;
    }else{
        index--;
    }
    goIndex();
}


window.onload=function(){
    setInterval(function(){
        goNext();
    },5000)
}
