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
    tm : null,

  _init : function(){
      var $this = this;
      $("#birthdate").inputmask("dd/mm/yyyy",{ "placeholder": "dd/mm/yyyy" });


      $("#zipCode").keyup(function(){
          $this._doTm($(this).val());
      });

  },
  _getTownsByZipcode : function(val){
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
  },

    _doTm : function(val){
        var $this = this;
      if($this.tm!=null)clearTimeout($this.tm);
      if(val.length < 3 )return;

      $this.tm = setTimeout(function(){
          $this._getTownsByZipcode(val);
      },300);



  }
};