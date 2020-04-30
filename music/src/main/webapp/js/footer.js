const ap = new APlayer({
    container: document.getElementById('player1'),
    listFolded: true,

    audio: [{
        name: 'トリカゴ',
        artist: 'XX:me',
        url: 'https://cn-south-17-aplayer-46154810.oss.dogecdn.com/darling.mp3',
        cover: 'images/bupt.jpg',
    }]
});
function playById (id) {
	var music = JSON.parse($("#music_"+id).attr("value"));
	ap.list.clear();
	ap.list.add([{
	    name: music.name,
	    artist: music.artist,
	    url: music.url,
	    cover: 'images/bupt.jpg',
	}]);
	ap.play();
}
