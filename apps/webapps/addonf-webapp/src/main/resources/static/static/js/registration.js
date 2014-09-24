/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 22/09/14
 * Time: 16:08
 */

var addonf = addonf || {};

addonf.Registration = function(){
    this._init();
};

addonf.Registration.prototype = {
  _init : function(){
      var $this = this;
      $("#birthdate").inputmask("dd/mm/yyyy",{ "placeholder": "dd/mm/yyyy" });


      $("#zipCode").keyup(function(){
          $this._getTownsByZipcode($(this).val());
      });

  },
  _getTownsByZipcode : function(val){
      if(val.length < 3 )return;

      $.ajax({
          url: addonf.base+'getTownsByZipcode/'+val
      }).done(function(data) {
          var $elt = $("#cityId");
          $elt.html("");

          for (var i = 0; i < data.length; i++) {
              var o = data[i];
              var $city = $("<option>").attr("val", o.id).html(o.city);
              $elt.append($city);
          }
      });

  }
};