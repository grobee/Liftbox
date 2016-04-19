var fileInput;
var filesDiv;
var progressBarContainerDiv;
var progressBarDiv;
var progressBarParentDiv;
var folderNameDiv;

function init() {
    fileInput = document.getElementById("fileUpload");
    filesDiv = document.getElementById("filesDiv");
    progressBarContainerDiv = document.getElementById("progressBarContainerDiv");
    progressBarDiv = document.getElementById("progressBarDiv");
    progressBarParentDiv = document.getElementById("progressBarParent");
    folderNameDiv = document.getElementById("folderName");

    filesDiv.className += "container-fluid";
    filesDiv.innerHTML = "No files selected...";
    filesDiv.style.textAlign = "center";

    progressBarParentDiv.removeChild(progressBarContainerDiv);
}

function upload() {
    if (fileInput.files.length == 0) {
        return;
    }

    doRealUpload(0);
}

function doRealUpload(i) {
    var xhr = new XMLHttpRequest();
    var fileDiv = document.getElementById("fileDiv" + i);

    fileDiv.appendChild(progressBarContainerDiv);

    var partialFormData = new FormData();
    partialFormData.append(folderNameDiv.innerHTML, fileInput.files[i]);

    xhr.open("post", "http://localhost:8080/liftbox/upload", true);

    xhr.upload.addEventListener("progress", function (e) {
        var percent = Math.ceil(e.loaded / e.total * 1000) / 10;
        progressBarDiv.style.width = parseInt(percent) + "%";
    });

    xhr.upload.addEventListener("loadend", function () {
        progressBarDiv.style.width = "0%";
        fileDiv.removeChild(progressBarContainerDiv);
        i++;

        if (i < fileInput.files.length) {
            doRealUpload(i);
        } else {
            console.log("ASDASDASD");
            window.location.replace(window.location);
        }
    });

    xhr.send(partialFormData);
}

function filesChanged() {
    filesDiv.innerHTML = "";

    for (var i = 0; i < fileInput.files.length; i++) {
        var file = fileInput.files[i];
        var fileDiv = document.createElement("div");
        var filePanelBodyDiv = document.createElement("div");
        var fileNameDiv = document.createElement("div");
        var fileSizeDiv = document.createElement("div");
        var fileUnitDiv = document.createElement("div");

        fileDiv.className += "fileDiv row panel panel-default";
        fileDiv.id = "fileDiv" + i;

        filePanelBodyDiv.className += "panel-body";

        fileNameDiv.innerHTML = file.name.length > 40 ? file.name.substr(0, 40) + "..." : file.name;
        fileSizeDiv.innerHTML = (file.size / (1024 * 1000)).toFixed(4);
        fileUnitDiv.innerHTML = "MB";

        fileNameDiv.className += "col-sm-8";
        fileNameDiv.style.textAlign = "left";

        fileSizeDiv.className += "col-sm-2";
        fileSizeDiv.style.textAlign = "right";

        fileUnitDiv.className += "col-sm-2";
        fileUnitDiv.style.textAlign = "left";

        filePanelBodyDiv.appendChild(fileNameDiv);
        filePanelBodyDiv.appendChild(fileSizeDiv);
        filePanelBodyDiv.appendChild(fileUnitDiv);

        fileDiv.appendChild(filePanelBodyDiv);
        filesDiv.appendChild(fileDiv);
    }
}

window.onload = init;
window.init = upload;

