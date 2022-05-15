$(() => {
    let tb = $("#tab").DataTable({
        language: {
            "emptyTable": "В таблице нет данных",
            "info": "Показано от _START_ до _END_ пользователей из _TOTAL_",
            "infoEmpty": "Нет пользователей",
            "infoFiltered": "(отфильтровано _MAX_ пользователей)",
            "lengthMenu": "Показать _MENU_ пользователей",
            "loadingRecords": "Загрузка...",
            "processing": "Идёт обработка...",
            "search": "Поиск:",
            "zeroRecords": "Пользователи не найдены",
            "paginate": {
                "first": "Первый",
                "last": "Посл",
                "next": "След",
                "previous": "Пред"
            }
        },
        columnDefs: [
            { orderable: false, targets: 4 },
        ],
        searching: false,
        lengthChange: false,
        pageLength: 30
    });
});