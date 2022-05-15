let isAdded = false;

window.onload = function () {
    const el = document.getElementById("avgResult");
    const avgResult = (parseFloat(el.textContent) * 100).toFixed();
    const ctx = document.getElementById("statChart");
    const data = {
        labels: ['правильные', 'неправильные'],
        datasets: [{
            label: '%',
            backgroundColor: ['green', 'red'],
            data: [avgResult, 100 - avgResult]
        }]
    };
    const config = {
        type: 'pie',
        data: data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Сравнительная статистика по всем данным ответам'
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            return context.raw + '%';
                        }
                    }
                }
            }
        }
    };
    const statChart = new Chart(ctx, config);
    el.remove();
}