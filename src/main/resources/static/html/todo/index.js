function clear(){
    localStorage.clear();
    load();
}

function post_action(){
    const title = document.getElementById("title");
    if(title.value === "") {
        alert("内容不能为空");
    }else{
        const data = loadData();
        const todo = {"title": title.value, "done": false};
        data.push(todo);
        saveData(data);
        const form = document.getElementById("form");
        form.reset();
        load();
    }
}

function loadData(){
    const collection = localStorage.getItem("todo");
    if(collection!=null){
        return JSON.parse(collection);
    }
    else return [];
}

function saveSort(){
    let todo;
    const todolist = document.getElementById("todolist");
    const donelist = document.getElementById("donelist");
    const ts = todolist.getElementsByTagName("p");
    const ds = donelist.getElementsByTagName("p");
    const data = [];
    for(let i=0;i<ts.length; i++){
        todo = {"title":ts[i].innerHTML,"done":false};
        data.unshift(todo);
    }
    for(i=0;i<ds.length; i++){
        todo = {"title": ds[i].innerHTML, "done": true};
        data.unshift(todo);
    }
    saveData(data);
}

function saveData(data){
    localStorage.setItem("todo",JSON.stringify(data));
}

function remove(i){
    const data = loadData();
    const todo = data.splice(i, 1)[0];
    saveData(data);
    load();
}

function update(i,field,value){
    const data = loadData();
    const todo = data.splice(i, 1)[0];
    todo[field] = value;
    data.splice(i,0,todo);
    saveData(data);
    load();
}

function edit(i)
{
    load();
    const p = document.getElementById("p-" + i);
    let title = p.innerHTML;
    p.innerHTML="<input id='input-"+i+"' value='"+title+"' />";
    const input = document.getElementById("input-" + i);
    input.setSelectionRange(0,input.value.length);
    input.focus();
    input.onblur =function(){
        if(input.value.length === 0){
            p.innerHTML = title;
            alert("内容不能为空");
        }
        else{
            update(i,"title",input.value);
        }
    };
}

function load(){
    const todolist = document.getElementById("todolist");
    const donelist = document.getElementById("donelist");
    const collection = localStorage.getItem("todo");
    if(collection!=null){
        const data = JSON.parse(collection);
        let todoCount = 0;
        let doneCount = 0;
        let todoString = "";
        let doneString = "";
        for (let i = data.length - 1; i >= 0; i--) {
            if(data[i].done){
                doneString+="<li draggable='true'><input type='checkbox' onchange='update("+i+",\"done\",false)' checked='checked' />"
                    +"<p id='p-"+i+"' onclick='edit("+i+")'>"+data[i].title+"</p>"
                    +"<a href='javascript:remove("+i+")'>-</a></li>";
                doneCount++;
            }
            else{
                todoString+="<li draggable='true'><input type='checkbox' onchange='update("+i+",\"done\",true)' />"
                    +"<p id='p-"+i+"' onclick='edit("+i+")'>"+data[i].title+"</p>"
                    +"<a href='javascript:remove("+i+")'>-</a></li>";
                todoCount++;
            }
        };
        todocount.innerHTML=todoCount;
        todolist.innerHTML=todoString;
        donecount.innerHTML=doneCount;
        donelist.innerHTML=doneString;
    }
    else{
        todocount.innerHTML=0;
        todolist.innerHTML="";
        donecount.innerHTML=0;
        donelist.innerHTML="";
    }

    const lis = todolist.querySelectorAll('ol li');
    [].forEach.call(lis, function(li) {
        li.addEventListener('dragstart', handleDragStart, false);
        li.addEventListener('dragover', handleDragOver, false);
        li.addEventListener('drop', handleDrop, false);

        onmouseout =function(){
            saveSort();
        };
    });
}

window.onload=load;

window.addEventListener("storage",load,false);

let dragSrcEl = null;

function handleDragStart(e) {
    dragSrcEl = this;
    e.dataTransfer.effectAllowed = 'move';
    e.dataTransfer.setData('text/html', this.innerHTML);
}
function handleDragOver(e) {
    if (e.preventDefault) {
        e.preventDefault();
    }
    e.dataTransfer.dropEffect = 'move';
    return false;
}
function handleDrop(e) {
    if (e.stopPropagation) {
        e.stopPropagation();
    }
    if (dragSrcEl !== this) {
        dragSrcEl.innerHTML = this.innerHTML;
        this.innerHTML = e.dataTransfer.getData('text/html');
    }
    return false;
}
