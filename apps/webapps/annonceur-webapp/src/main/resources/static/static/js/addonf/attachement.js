/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 10/04/14
 * Time: 21:13
 * To change this template use File | Settings | File Templates.
 */

var Addonf =  Addonf || {};
Addonf.Enroll =  Addonf.Enroll || {};
Addonf.Enroll.attachement = {};

Addonf.Enroll.attachement = function(options){
    this._init(options)
}

Addonf.Enroll.attachement.prototype = {

    pluploads : [],
    options :null,

    _createPlUpload : function(who){
        var $this = this;
        return new plupload.Uploader({
            runtimes : 'html5,flash,silverlight,html4',

            browse_button : 'pickup_'+who,
            container: document.getElementById('container_'+who),

            url : this.options.url.replace('_fileId',who),

            filters : {
                max_file_size : '1.5mb',
                mime_types: [
                    {title : "Fichier Image", extensions : "jpg,gif,png"},
                    {title : "Document", extensions : "pdf,doc,docx"}
                ]
            },

            // Flash settings
            flash_swf_url : this.options.flash_swf_url,

            // Silverlight settings
            silverlight_xap_url : this.options.silverlight_xap_url,


            init: {
                PostInit: function() {

                },

                FilesAdded: function(up, files) {
                    plupload.each(files, function(file) {
                        $("#indicate_"+who).html(file.name);
                        $("#error_"+who).hide();
                        up.start();
                        $("#progress_"+who).show();
                        $("#continue").hide();
                        $("#back").hide();
                    });
                },

                UploadProgress: function(up, file) {
                    var progress = $("#progress_"+who);
                    progress.find(".progress-bar").css("width",file.percent);
                    progress.find("span").html(file.percent + "%");
                    //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
                },

                FileUploaded : function(up, file,response) {
                    var resJson = $.parseJSON(response.response);
                    $("#progress_"+who).hide();

                    $("#continue").show();
                    $("#back").show();

                    if(resJson.error == 'no'){
                        $("#status_"+who).html($this.options.strStatusPresent);
                    }else{
                        $("#error_"+who).html($this.options.errors[resJson.error]).show();
                    }

                },

                Error: function(up, err) {
                    $("#error_"+who).html(err.code + ": " + err.message).show();
                    $("#continue").show();
                    $("#back").show();
                }
            }
        });
    },


    _init : function(options){
        this.options = options;
        for (var i = 0; i < this.options.files.length; i++){
            this.pluploads[i] = this._createPlUpload(this.options.files[i]);
            this.pluploads[i].init();
        }
    }
}
