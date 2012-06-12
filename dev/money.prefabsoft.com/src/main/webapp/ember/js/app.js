var App = Em.Application.create({
	displayError : function(e){
		console.error(e);
	}
});

App.MyView = Em.View.extend({
	mouseDown : function() {
		window.alert("hello world!");
	}
});

App.ItemTag = Ember.Resource.extend({
	resourceUrl : 'http://localhost:8080/prefabsoft.com/itemtags',
	resourceName : 'itemtag',
	resourceProperties : [ 'id', 'name' ],

	validate : function() {
		if (this.get('id') === undefined || this.get('id') === ''
				|| this.get('name') === undefined || this.get('name') === '') {
			return 'ItemTags require an id and a name.';
		}
	},

	fullName : Ember.computed(function() {
		return this.get('name') + ' ' + this.get('id');
	}).property('name', 'id')
});

var itemTag = App.ItemTag.create({
	id : 3,
	name : 'test'
});

itemTag.saveResource().fail(function(e) {
	App.displayError(e);
}).done(function() {
	console.log('itemTag saved');
});

var itemTag2 = itemTag.findResource().fail(function(e) {
	App.displayError(e);
}).done(function() {
	console.log('itemTag loaded');
});

console.log(itemTag2);