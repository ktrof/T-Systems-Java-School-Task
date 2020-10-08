let addSectionRow = function() {
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
            let stationInput = document.getElementById('stationName');
            let input = newInput(listName, rowIndex, fieldName);
            input.readOnly = true;
            input.value = stationInput.value;
            col.appendChild(input);
        } else if (fieldName === 'stationDtoTo'){
            let select = document.createElement('select');
            select.classList.add('form-control');
            select.id = listName + rowIndex + '.' + fieldName;
            select.setAttribute('name', listName + '[' + rowIndex + '].' + fieldName);
            $('#dropdownStationList option').clone().appendTo(select);
            col.appendChild(select);
        } else {
            col.appendChild(deleteButton(rowIndex.toString()));
        }
        row.appendChild(col);
    });

    document.getElementById('sectionList').appendChild(row);
};

let addScheduleSectionRow = function() {
    let listName = 'scheduleSectionDtoArray';
    let fieldsNames = ['sectionDtoId', 'stopDuration', 'indexWithinTrainRoute', 'deleteButton'];
    let rowIndex = document.querySelectorAll('.item').length;

    let row = document.createElement('div');
    row.classList.add('row', 'item');
    row.id = rowIndex.toString();

    fieldsNames.forEach((fieldName) => {
        let col = document.createElement('div');

        if (fieldName === 'sectionDtoId'){
            col.classList.add('col', 'form-group');
            let select = document.createElement('select');
            select.classList.add('form-control');
            select.id = listName + rowIndex + '.' + fieldName;
            select.setAttribute('name', listName + '[' + rowIndex + '].' + fieldName);
            $('#dropdownSectionList option').clone().appendTo(select);
            col.appendChild(select);
        } else if (fieldName === 'stopDuration') {
            col.classList.add('col', 'col-md-2', 'form-group');
            let input = newInput(listName, rowIndex, fieldName);
            col.appendChild(input);
        } else if (fieldName === 'indexWithinTrainRoute') {
            col.classList.add('col', 'col-md-2', 'form-group');
            let input = newInput(listName, rowIndex, fieldName);
            input.readOnly = true;
            input.value = rowIndex.toString();
            col.appendChild(input);
        } else {
            col.classList.add('col-md-1', 'form-group');
            col.appendChild(deleteButton(rowIndex.toString()));
        }
        row.appendChild(col);
    });

    document.getElementById('scheduleSectionList').appendChild(row);
};

let deleteButton = function(rowIndex) {
    let deleteButton = document.createElement('button');
    deleteButton.classList.add('btn', 'btn-danger');
    deleteButton.type = 'button';
    deleteButton.textContent = 'Delete row';
    deleteButton.addEventListener('click', function() {
        let element = document.getElementById(rowIndex);
        return element.remove();
    });
    return deleteButton;
};

let newInput = function(listName, rowIndex, fieldName) {
    let input = document.createElement('input');
    input.type = 'text';
    input.classList.add('form-control');
    input.id = listName + rowIndex + '.' + fieldName;
    input.setAttribute('name', listName + '[' + rowIndex + '].' + fieldName);
    return input;
};

$(function() {
    $('#dateRange').datepicker({
        range: 'multiple',
        dateFormat: 'yy-mm-dd',
        onSelect: function(dateText, inst, extensionRange) {
            $('#trainRideDates').val(extensionRange.datesText.join(','));
        }
    });

    let extensionRange = $('#dateRange').datepicker('widget').data('datepickerExtensionRange');
    if (extensionRange.datesText) $('#trainRideDates').val(extensionRange.datesText.join(','));
});

$(function () {
    $('#datetimepickerDeparture').datetimepicker({
        format: 'HH:mm'
    });
});
