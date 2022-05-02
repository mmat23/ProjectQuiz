let isAdded = false;

window.onload = function () {
    const template = document.getElementById("card-ques");
    document.getElementById("btn-add").onclick = function () {
        let count = parseInt(document.getElementById("res").value);
        let cardsDiv = document.getElementById("cards-ques");
        let cards = cardsDiv.getElementsByClassName("row-ques");
        let countCards = cards.length;
        if (isAdded != true) {
            this.textContent = "Обновить формы";
            let el = document.createElement("div");
            el.innerHTML = '<div class="row justify-content-center pb-3"><div class="col-6"><div class="card"><div class="card-body text-center"><h6>Заполните вопросы ниже</h6></div></div></div></div>';
            cardsDiv.parentElement.insertBefore(el.firstChild, cardsDiv);
            el = document.createElement("div");
            el.innerHTML = '<div class="row justify-content-center pb-3"><div class="col-auto"><input class="btn btn-primary" type="submit" value="Начать заполнение вопросов" /></div></div>';
            cardsDiv.parentElement.append(el.firstChild);
            isAdded = true;
        }
        if (count > countCards) {
            for (let i = countCards; i < count; i++) {
                let copy = template.content.cloneNode(true);
                let area = copy.querySelector('textarea');
                area.name = area.name.replace(/#/, i);
                let input = copy.querySelector('.input-group');
                for (let j = 0; j < 3; j++) {
                    input.parentElement.append(input.cloneNode(true));
                }
                let groups = copy.querySelectorAll('.input-group');
                groups.forEach((el, j) => {
                    let inputs = el.querySelectorAll('input');
                    inputs[0].name = inputs[0].name.replace(/#/, i);
                    inputs[1].name = inputs[1].name.replace(/#/, i).replace(/&/, j);
                });
                cardsDiv.append(copy);
            }
        }
        else if (count < countCards) {
            while (cards[count])
                cards[count].remove();
        }
    };
}