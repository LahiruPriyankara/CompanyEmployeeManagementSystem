/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
//        $('#mainTable').DataTable({
//            "bJQueryUI": true,
//            "dom": '<lf<t>ip>',
//            "lengthMenu": [[15, 50, 100, -1], [15, 50, 100, "All"]]
//        });

    /*    $("a").click(function () {
     var id = $(this).attr("id");
     if (id !== "existingTab" && id !== "pendingTab") {
     $(".titleLink").css('background', '#ff751a');
     if (id === "pageTitle") {
     $("#home").css('background', '#cc5200');
     } else if (id !== "userData") {
     $(this).css('background', '#cc5200');
     }
     }
     });*/
});


/* alert javaScript-START -----------------------------------------------------------------------------------------------------------*/
var ALERT_TITLE = "OOPS..!";
var ALERT_BUTTON_TEXT = "Ok";

if (document.getElementById) {
    window.alert = function (txt) {
        createCustomAlert(txt);
    }
}

function createCustomAlert(txt) {
    d = document;

    if (d.getElementById("modalContainer"))
        return;

    mObj = d.getElementsByTagName("body")[0].appendChild(d.createElement("div"));
    mObj.id = "modalContainer";
    mObj.style.height = d.documentElement.scrollHeight + "px";

    alertObj = mObj.appendChild(d.createElement("div"));
    alertObj.id = "alertBox";
    if (d.all && !window.opera)
        alertObj.style.top = document.documentElement.scrollTop + "px";
    alertObj.style.left = (d.documentElement.scrollWidth - alertObj.offsetWidth) / 2 + "px";
    alertObj.style.visiblity = "visible";

    h1 = alertObj.appendChild(d.createElement("h1"));
    h1.appendChild(d.createTextNode(ALERT_TITLE));

    msg = alertObj.appendChild(d.createElement("p"));
    //msg.appendChild(d.createTextNode(txt));
    msg.innerHTML = txt;

    btn = alertObj.appendChild(d.createElement("a"));
    btn.id = "closeBtn";
    btn.appendChild(d.createTextNode(ALERT_BUTTON_TEXT));
    btn.href = "#";
    btn.focus();
    btn.onclick = function () {
        removeCustomAlert();
        return false;
    }

    alertObj.style.display = "block";

}

function removeCustomAlert() {
    document.getElementsByTagName("body")[0].removeChild(document.getElementById("modalContainer"));
}
function ful() {
    alert('Alert this pages');
}

/*alert javaScript-END -----------------------------------------------------------------------------------------------------------*/





//all check box selection
/*var isSelectAll = false;
function selectAll(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    isSelectAll = source.checked;
    console.log("isSelectAll : " + isSelectAll);
    document.getElementsByName("myText").readOnly = true;
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source) {
            checkboxes[i].checked = source.checked;
            //isSelectAll = source.checked;
            //console.log("isSelectAll : " + isSelectAll);
        }
    }
}*/

myFunction();

function clickOnTab(uri) {
	console.log(uri);
    if (uri !== "") {
        hidePage();
        $.post(uri, {}, function (data) {
            $('#pageBody').empty();
            $('#pageBody').append(data);
            showPage();
        });
    }
}


function getDetails(id, uri) {
    $("#saveBtn").hide();
    if (uri !== "") {
        hidePage();
        $.post(uri, {id: id}, function (data) {
            $('#modelDivData').empty();
            $('#modelDivData').append(data);
            showPage();
        });
    }

    document.getElementById("btnForModel").click();
}




/*function acceptChanges(id, url) {
    var status = document.getElementById("status").value;
    alert("Are you want to accept..! " + id + " - " + status);
}*/



//saveReModificationFdUser
/*function saveFdUser(uri) {
    var id = document.getElementById("tempId").value;
    var userName = document.getElementById("userName").value;
    var userFirstName = document.getElementById("userFirstName").value;
    var userLastName = document.getElementById("userLastName").value;

    if (userName === "" || userFirstName === "" || userLastName === "") {
        alert("Please fill all input field..! ");
    } else {
        if (uri !== "") {
            hidePage();
            $.post(uri, {id: id, userName: userName, userFirstName: userFirstName, userLastName: userLastName}, function (data) {
                $('#pageBody').empty();
                $('#pageBody').append(data);
                showPage();
            });
        }
    }

}*/

/*
function saveBankUser() {
    //alert("Are you want to save.");
    $("#bankUserDetails").submit();
    getWaitingDivDisplayed();
}
*/
