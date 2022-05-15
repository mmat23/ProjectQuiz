$(() => {
    $('#quizTab tfoot th').each(function () {
        let title = $(this).text();
        if (title)
            $(this).html('<input type="text" placeholder="' + title + '" />');
    });
    let tab = $("#quizTab").DataTable({
        initComplete: function () {
            // Apply the search
            this.api()
                .columns()
                .every(function () {
                    var that = this;

                    $('input', this.footer()).on('keyup change clear', function () {
                        if (that.search() !== this.value) {
                            that.search(this.value).draw();
                        }
                    });
                });
        },
        language: {
            "emptyTable": "В таблице нет данных",
            "info": "Показано от _START_ до _END_ викторин из _TOTAL_",
            "infoEmpty": "Нет викторин",
            "infoFiltered": "(отфильтровано _MAX_ викторин)",
            "lengthMenu": "Показать _MENU_ викторины",
            "loadingRecords": "Загрузка...",
            "processing": "Идёт обработка...",
            "search": "Поиск:",
            "zeroRecords": "Викторины не найдены",
            "paginate": {
                "first": "Первый",
                "last": "Посл",
                "next": "След",
                "previous": "Пред"
            }
        },
        columnDefs: [
            { className: "dt-center", targets: 3 }
        ],
        ordering: false,
        lengthChange: false,
        pageLength: 50
    });
});