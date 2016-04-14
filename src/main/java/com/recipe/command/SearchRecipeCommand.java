package com.recipe.command;
/*
 * https://scalegrid.io/blog/fast-paging-with-mongodb/
 * var simpleSearch = function(request,response){
	var query = request.params.query;
	var pagenum = request.params.pagenum;
	var startIndex = (pagenum-1)*3;

	console.log("Query for: "+query+', page number: '+pagenum);

	Post.search({
		"query":{
			"fuzzy":{
				"author":{
					"value":query,
					"fuzziness":2
				},
				"career_title":{
					"value":query,
					"fuzziness":2
				}
			}
		}
	}, function(err,result){
		console.log("Fuzzy search made.");
		//console.log("Result: "+JSON.stringify(result));
		console.log(JSON.stringify(result.hits.hits));
		var career_titles = [];
		var authors = [];
		var descriptions = [];
		var _ids = [];

		for (var i = startIndex; i < startIndex+3 && i < result.hits.hits.length; i++){
			career_titles.push(result.hits.hits[i]._source.career_title);
			authors.push(result.hits.hits[i]._source.author);
			descriptions.push(result.hits.hits[i]._source.description);
			_ids.push(result.hits.hits[i]._id);
		}

		console.log(career_titles[0]);

		response.render('searchResult',
			{
				'career_titles' : {
					title1 : career_titles[0],
					title2 : career_titles[1],
					title3 : career_titles[2]
				},
				'authors' : {
					author1 : authors[0],
					author2 : authors[1],
					author3 : authors[2]
				},
				'descriptions' : {
					description1 : descriptions[0],
					description2 : descriptions[1],
					description3 : descriptions[2]
				},
				'_ids' : {
					id1 : _ids[0],
					id2 : _ids[1],
					id3 : _ids[2]
				}
			});

	});
}
 */
class SearchRecipeCommand {
	
}