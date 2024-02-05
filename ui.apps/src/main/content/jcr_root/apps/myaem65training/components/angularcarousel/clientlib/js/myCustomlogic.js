(function() {
    "use strict";
      angular.module("angularcarouselapp", ['HiggidyCarousel'])
        .controller('angularcarouselappController', function($scope) {
          $scope.images = [
            {
              src: "./images/higgidy_1.jpg",
              alt: "image 1",
              link: "http://www.higgidy.co.uk"
            },
            {
              src: "./images/higgidy_2.jpg",
              alt: "image 2",
              link: "http://www.higgidy.co.uk"
            },
            {
              src: "./images/higgidy_3.jpg",
              alt: "image 3",
              link: "http://www.higgidy.co.uk"
            }
          ]
        });
})();