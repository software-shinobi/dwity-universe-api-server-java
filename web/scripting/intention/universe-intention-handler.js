$(document).ready(function () {

    getUniverseIntention();

});

function getUniverseIntention() {

	console.debug("enter -> getUniverseIntention");

//////////////////////////////////////////

	$.ajax({

		type: "GET",

		url: upstreamAPIURL + "/intention/",

		crossDomain: true,

		dataType: "text",

		success: function (response, status, jqXHR) {

            console.log("response / " + response);

            intention=response;

            console.log("intention / server / " + intention);

            $("#intention").text(intention);

		},

		error: function (exception, status) {

			console.log("error issuing request");

			console.log("status / " + status);

			console.log("exception / " + exception);

		}

	});

}

function processForm() {

	console.debug("enter -> processForm");

    intention=$("#intention").val();

    //alert("intention / " + intention);

//////////////////////////////////////////

	payload = JSON.stringify({

        intention: intention,        

	});

    //alert("payload / ", payload);

//////////////////////////////////////////

	$.ajax({

		type: "POST",

		url: upstreamAPIURL + "/intention/set-intention",

		data: payload,

		contentType: "application/json; charset=utf-8",

		crossDomain: true,

		dataType: "text",

		success: function (response, status, jqXHR) {

            console.log("response / " + response);

            alert(response);

            getUniverseIntention();

		},

		error: function (exception, status) {

			console.log("error issuing request");

			console.log("status / " + status);

			console.log("exception / " + exception);

		}

	});

}
