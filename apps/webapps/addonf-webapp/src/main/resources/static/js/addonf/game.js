var addonf = addonf||{};

addonf.AdGame = function() {
    this._init();
};

/**
 *
 */
addonf.AdGame.prototype = {
    gameData : null,

    timerGame : null,
    timerAd : null,

    questionIndex : null,


    /**
     *
     * @private
     */
    _init : function(){
        // 1 -passage en mode plein ecran
        addonf.requestFullScreen(document.body);
        // 2 - demande de création du jeu au serveur
        this.startGame();

    },

    /**
     * Permet de démarrer le jeu
     */
    startGame : function(){
        var that = this;
        $.ajax({
            url: "/rest/createGame"
        }).done(function(data) {
            that.gameData = data;
            that._onGameCreated();
        });
    },

    /**
     *
     * @private
     */
    _onGameCreated : function(){
        // 1 - affichage du tableau de jeu
        this._drawGame();
        // 2 - affichage de la premier question
        this._drawQuestion();

    },

    /**
     *
     * @private
     */
    _drawGame : function (){
        // 1 - affichage de overlay de loading
        this._displayLoading();
        // 2 - initialisation du jeu
        this.questionIndex = 0;
        this._drawQuestion();
        // 4 - démarrage du timer de jeu
        this.timerGame = new Date().getTime();
        // 3 - cacher overlay de loading
        this._hideLoading();
    },

    /**
     *
     * @private
     */
    _displayLoading : function (){

    },

    /**
     *
     * @private
     */
    _hideLoading : function (){

    },

    /**
     *
     * @private
     */
    _drawQuestion : function (){
        // 1 - affichage de la question

        // 2 - mise en place des réponses

        // 3 - démarrage du timer de pub
    },

    /**
     *
     * @private
     */
    _onPlayerKick : function(){
        // 1 - envoi de la reponse au serveur
        var that = this;
        $.ajax({
            url: "/rest/playerKick",
            data : {
                // Todo: créer le bon objet
            }
        }).done(function(data) {
                that._onResponseServer(data);
        });
        // 2 - passage à la question suivant
        this.questionIndex ++;
        this._drawQuestion();
    },


    /**
     *
     * @param data
     * @private
     */
    _onResponseServer : function (data){
        // 1 - afficher si la reponse est correcte ou non

        // 2 - affichage du nouveau score

        // 3 - si fin du jeu affichage du resultat
        this.timerAd = new Date().getTime();

    },

    _onNoresponsePlayer : function (){

    },

    /**
     *
     * @private
     */
    _onEndTime : function(){

    }
}