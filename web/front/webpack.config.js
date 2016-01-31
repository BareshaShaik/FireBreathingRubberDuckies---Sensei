module.exports = {
	output: {
		filename: 'bundle.js'
	},

	module: {
		loaders: [
			{ test: /\.scss$/, loader: 'style!css!sass' },
			{ test: /\.jade$/, loader: 'template-html-loader' },
			{ 
				test: /\.js$/, 
				loader: 'babel', 
				exclude: ['/client\/lib/', '/node_modules/', '/\.spec\.js/'],
				query: { presets: ['es2015']}
			}
		]
	}
};