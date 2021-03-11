        var firstSeatLabel = 1;

        $(document).ready(function() {
            var $cart = $('#selected-seats'),
                $counter = $('#counter'),
                $total = $('#total'),
                sc = $('#seat-map').seatCharts({
                    map: [
                        'ttt_tt',
                        'ttt_tt',
                        'fff_ff',
                        'fff_ff',
                        'fff___',
                        'fff_ff',
                        'fft_tt',
                        'tff_ff',
                        'fff_ff',
                    ],
                    seats: {
                        // 可以自訂class利用CSS調整數值
                        t: {
                            price: 100,
                            classes: 'first-class', //your custom CSS class
                            category: 'First Class'
                        },


                    },
                    naming: {
                        // 顯示上方編號
                        // top: false,

                        // 顯示側邊編號
                        // left: false;

                        // 調整上方編號
                        columns: ['01', '02', '03', '', '04', '05'],

                        // 調整側邊編號
                        rows: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'],
                        // 調整格子編號
                        getLabel: function(character, row, column) {
                            return +column;
                        },
                        // 調整ID
                        getId: function(character, row, column) {
                            return row + '' + column;
                        }
                    },
                    // 右側說明欄
                    legend: {
                        node: $('#legend'),
                        items: [
                            ['t', 'available', '可選擇'],
                            ['t', 'selected', '你的座位'],
                            ['f', 'unavailable', '已售出']
                        ]
                    },
                    click: function() {
                        if (this.status() == 'available') {
//                            var maxChoose = 2;
                            var nowChoose = sc.find('selected').length;
                            if (nowChoose < maxChoose) {
                                //let's create a new <li> which we'll add to the cart items
                                $('<li>' + this.data().category + ' Seat # ' + this.settings.label + ': <b>$' + this.data().price + '</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
                                    .attr('id', 'cart-item-' + this.settings.id)
                                    .data('seatId', this.settings.id)
                                    .appendTo($cart);

                                /*
                                 * Lets update the counter and total
                                 *
                                 * .find function will not find the current seat, because it will change its stauts only after return
                                 * 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
                                 */
                                $counter.text(sc.find('selected').length + 1);
                                $total.text(recalculateTotal(sc) + this.data().price);

                                return 'selected';
                            } else {
                                alert("不能在選了");
                                return 'available';
                            }
                        } else if (this.status() == 'selected') {
                            //update the counter
                            $counter.text(sc.find('selected').length - 1);
                            //and total
                            $total.text(recalculateTotal(sc) - this.data().price);

                            //remove the item from our cart
                            $('#cart-item-' + this.settings.id).remove();

                            //seat has been vacated
                            return 'available';
                        } else if (this.status() == 'unavailable') {
                            //seat has been already booked
                            return 'unavailable';
                        } else {
                            return this.style();
                        }

                    }
                });

            //this will handle "[cancel]" link clicks
            $('#selected-seats').on('click', '.cancel-cart-item', function() {
                //let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
                sc.get($(this).parents('li:first').data('seatId')).click();
            });

            //let's pretend some seats have already been booked
            // sc.get(['1_2', '4_1', '7_1', '7_2']).status('unavailable');

            // 將所有e改為已被選擇
            sc.find('f').status('unavailable')

        });

        function recalculateTotal(sc) {
            var total = 0;

            //basically find every selected seat and sum its price
            sc.find('selected').each(function() {
                total += this.data().price;
            });

            return total;
        }