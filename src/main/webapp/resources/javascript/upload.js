function upload() {
    var xhr = new XMLHttpRequest();
    var formData = new FormData();
    var fileInput = document.getElementById("fileUpload");

    console.log(fileInput);

    for (var i = 0; i < fileInput.files.length; i++) {
        formData.append("file" + i, fileInput.files[i]);
    }
    
    xhr.open("post", "http://localhost:8080/liftbox/upload", true);
    xhr.send(formData);
}

window.init = upload;

