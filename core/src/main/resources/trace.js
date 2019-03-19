"use strict";

function collapse (statementId) {
    var elements = document.querySelectorAll('[id^=\'' + statementId + '\']');
    console.log('collapse ' + statementId + ' :' + elements.length);

    //skip zero element
    for(var i = 1; i < elements.length; i++) {
        elements[i].style.display='none';
    }

    document.getElementById('col_' + statementId).style.display='none';
    document.getElementById('ex_' + statementId).style.display='inline';
}

function expand (statementId) {
    var elements = document.querySelectorAll('[id^=\'' + statementId + '\']');
    console.log('expand ' + statementId + ' :' + elements.length);

    //skip zero element
    for(var i = 1; i < elements.length; i++) {
        elements[i].style.display='block';
    }

    document.getElementById('col_' + statementId).style.display='inline';
    document.getElementById('ex_' + statementId).style.display='none';
}
