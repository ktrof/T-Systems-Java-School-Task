let addRow = function () {
    let listName = 'correspondingSectionList';
    let fieldsNames = ['stationDtoFrom', 'stationDtoTo', 'deleteButton'];
    let rowIndex = document.querySelectorAll('.item').length;

    let row = document.createElement('div');
    row.classList.add('row', 'item');
    row.id = rowIndex.toString();

    fieldsNames.forEach((fieldName) => {
        let col = document.createElement('div');
        col.classList.add('col', 'form-group');

        if (fieldName === 'stationDtoFrom') {
            let stationInput = document.getElementById('name');
            let input = document.createElement('input');
            input.type = 'text';
            input.classList.add('form-control');
            input.id = listName + rowIndex + '.' + fieldName;
            input.value = stationInput.value;
            input.setAttribute('name', listName + '[' + rowIndex + '].' + fieldName);
            col.appendChild(input);
        } else if (fieldName === 'stationDtoTo'){
            let select = document.createElement('select');
            select.classList.add('form-control');
            select.id = listName + rowIndex + '.' + fieldName;
            select.setAttribute('name', listName + '[' + rowIndex + '].' + fieldName);
            $('#dropdownList option').clone().appendTo(select);
            col.appendChild(select);
        } else {
            let deleteButton = document.createElement('button');
            deleteButton.classList.add('btn', 'btn-danger');
            deleteButton.type = 'button';
            deleteButton.textContent = 'Delete row';
            deleteButton.addEventListener('click', function () {
                let element = document.getElementById(rowIndex.toString());
                return element.remove();
            });
            col.appendChild(deleteButton);
        }
        row.appendChild(col);
    });

    document.getElementById('sectionList').appendChild(row);
};
