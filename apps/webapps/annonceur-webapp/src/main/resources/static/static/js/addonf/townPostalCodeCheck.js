(function($){

    /**
     * get an element by id. Handle dots by escaping them.
     * @param id the id to lookup.
     * @return the jQuery object.
     */
    var getById = function (id) {
        return $("#" + id.replace(/\./g, '\\.'));
    };

    var replaceAndTrigger = function (current, next) {
        var $next = $(next);
        current.replaceWith($next);
        $next.trigger("change", [{
            ignoreError : false
        }]);
    };

    var createInput = function ($town) {
        return '<input type="text" name="' + $town.attr("name") + '" id="' + $town.attr("id") + '" class="'+ $town.attr("class") + '"/>';
    };

    /**
     * Associate an action with a locale.
     */
    var actionPerCountry = {
        DEFAULT : function (postalCodeInput, settings) {
            $(postalCodeInput).attr("maxlength", 5);
        },
        LU : function (postalCodeInput, settings) {
            $(postalCodeInput).attr("maxlength", 4);
        }
    };

    /**
     * Retrieve the towns an fill in a <select>.
     * @param country the jQuery object for the country input.
     * @param postalCode the jQuery object for the postal code.
     * @param settings the settings used when this plugin was called.
     */
    var getTownsWithAjax = function(country, postalCode, settings) {

        if (!postalCode || !postalCode.val()/* || !country || !country.val()*/) {
            return;
        }

        var currentTown  = getById(settings.townId),
            townName     = currentTown.attr("name"),
            townId       = currentTown.attr("id"),
            townClass    = currentTown.attr("class"),
            selectedTown = currentTown.val();

        var countryVal = (!country.val())?"FR":country.val();

        $.getJSON(settings.baseUrl + 'getTowns/' + countryVal + '/' + postalCode.val(), function(data) {
            var formInput = '';
            if(data.length === 0){
                formInput = createInput(currentTown);
                // show the user a hint on the postal code. Might be a wrong postal code.
                postalCode.trigger("hint.kwixo", [{
                    text : settings.invalidPostalCodeHint
                }]);
            } else {
                formInput = '<select name="' + townName + '" id="' + townId + '" class="'+ townClass + '">';
                if(data.length > 1){
                    formInput += '<option value="">' + settings.placeHolder + '</option>';
                }
                for (var i = 0; i < data.length; i++) {
                    var town = data[i];
                    if(town == selectedTown){
                        formInput += '<option selected value="' + town + '">' + town + '</option>';
                    }else{
                        formInput += '<option value="' + town + '">' + town + '</option>';
                    }
                }
                formInput += '</select>';
                if(!country.val()){
                    country.val('FR');
                }
            }

            // we must override it since it can be a text input.
            replaceAndTrigger(currentTown, formInput);
        });
    };

    $.fn.postalCodeConsistency = function (options) {
        var settings = $.extend({
            // base url for the ajax request.
            baseUrl : "/",
            // String : id of the town input.
            townId : null,
            // String : id of the country input.
            countryId : null,
            // placeholder for the <select>
            placeHolder : "Sélectionner",
            // allow the user to put what he want if he select allowUserInputFor as postal code.
            allowUserInput : false,
            // the value triggering the above behavior.
            allowUserInputFor : "99000",
            // hint shown if the service didn't find any town
            invalidPostalCodeHint : "Merci de vérifier votre saisie: le code postal ne correspond pas au pays sélectionné"
        }, options),
            country = getById(settings.countryId),
            postalCodeInput = this;

        // already have a value, trigger the ajax call to get the towns.
        getTownsWithAjax(country, this, settings);

        // bind the country change : if the country change, it will have consequences.
        country.bind("change", function() {
            if (settings.allowUserInput && settings.allowUserInputFor == postalCodeInput.val()) {
                return;
            }
            var action = actionPerCountry[$(this).val()] || actionPerCountry['DEFAULT'];
            action && action.call(this, postalCodeInput, settings);
            getTownsWithAjax(country, postalCodeInput, settings);
        });

        // bind the postal code change.
        this.bind("keyup change", function() {
            var $this      = $(this),
                postalCode = $this.val(),
                town       = getById(settings.townId);

            if ($this.data("previousCode") == postalCode) {
                // nothing to do
                return;
            } else {
                $this.data("previousCode", postalCode);
            }

            if (postalCode === null || postalCode.length < $this.attr("maxlength")) {
                return;
            }

            if (settings.allowUserInput && settings.allowUserInputFor == postalCode) {
                // replace because it is usually a select.
                replaceAndTrigger(town, createInput(town));
                return;
            }

            // go !
            getTownsWithAjax(country, $this, settings);
        });
    };
})(jQuery);