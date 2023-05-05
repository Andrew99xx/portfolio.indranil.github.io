/**
 * Sends the form data to the server via AJAX
 */
function submitForm() {
  $('#loader').show();
  var name = $("#name").val();
  var file = $("#image").val();
  var description = $("#description").val();
  var form = $("#form").serialize();
  var data = new FormData($("#form")[0]);
  data.append('name', name);
  data.append('description', description);
  if (name === "" || file === "" || description === "") {
    $("#submit").prop("disabled", false);
    $('#loader').hide();
    $("#name").css("border-color", "red");
    $("#image").css("border-color", "red");
    $("#description").css("border-color", "red");
    $("#error_name").html("Please fill the required field.");
    $("#error_file").html("Please fill the required field.");
    $("#error_price").html("Please fill the required field.");
    $("#error_description").html("Please fill the required field.");
  } else {
    $("#name").css("border-color", "");
    $("#image").css("border-color", "");
    $("#description").css("border-color", "");
    $('#error_name').css('opacity', 0);
    $('#error_file').css('opacity', 0);
    $('#error_description').css('opacity', 0);
    $.ajax({
      type: 'POST',
      enctype: 'multipart/form-data',
      data: data,
      url: "/image/saveImageDetails",
      processData: false,
      contentType: false,
      cache: false,
      success: function(data, statusText, xhr) {
        console.log(xhr.status);
        if (xhr.status == "200") {
          $('#loader').hide();
          $("#form")[0].reset();
          $('#success').css('display', 'block');
          $("#error").text("");
          $("#success").html("Product Inserted Succsessfully.");
          $('#success').delay(100000).fadeOut('slow');
        }
      },
      error: function(e) {
        $('#loader').hide();
        $('#error').css('display', 'block');
        $("#error").html("Oops! something went wrong.");
        $('#error').delay(100000).fadeOut('slow');
        location.reload();
      }
    });
  }
}

function submitForm2() {
  $('#loader').show();
  var file = $("#image").val();
  var form = $("#form").serialize();
  var data = new FormData($("#form")[0]);
  if (file === "") {
    $("#submit").prop("disabled", false);
    $('#loader').hide();
    $("#image").css("border-color", "red");
    $("#error_file").html("Please fill the required field.");
    
  } else {
    $("#image").css("border-color", "");
    $('#error_file').css('opacity', 0);
    $.ajax({
      type: 'POST',
      enctype: 'multipart/form-data',
      data: data,
      url: "/cv/saveCv",
      processData: false,
      contentType: false,
      cache: false,
      success: function(data, statusText, xhr) {
        console.log(xhr.status);
        if (xhr.status == "200") {
          $('#loader').hide();
          $("#form")[0].reset();
          $('#success').css('display', 'block');
          $("#error").text("");
          $("#success").html("Product Inserted Succsessfully.");
          $('#success').delay(100000).fadeOut('slow');
        }
      },
      error: function(e) {
        $('#loader').hide();
        $('#error').css('display', 'block');
        $("#error").html("Oops! something went wrong.");
        $('#error').delay(100000).fadeOut('slow');
        location.reload();
      }
    });
  }
}

// Call the submitForm() function when the submit button is clicked
$(document).ready(function() {
  $('#loader').hide();
  $("#submit").on("click", function() {
    submitForm();
  });
});

$(document).ready(function() {
  $('#loader').hide();
  $("#submit2").on("click", function() {
    submitForm2();
  });
});