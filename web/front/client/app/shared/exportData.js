const exportData = () => {
	const generateCSV = (data) => {
		var csvContent = "data:text/csv;charset=utf-8,";
		csvContent += data;
		var encodedUri = encodeURI(csvContent);
		
		return encodedUri;
	};

	const convertToCSV = (objArray) => {
            var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
            var str = '';
            for (var i = 0; i < array.length; i++) {
                var line = '';
                for (var index in array[i]) {
                    if (line != '') line += ','

                    line += array[i][index];
                }

                str += line + '\r\n';
            }

            return str;
     }

	return {generateCSV, convertToCSV};
};

export {exportData};