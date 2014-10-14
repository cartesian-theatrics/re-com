(ns re-demo.tour
  (:require [re-com.util     :as    util]
            [re-com.core     :refer [button label input-text checkbox]]
            [re-com.box      :refer [h-box v-box box gap]]
            [re-com.dropdown :refer [single-dropdown find-choice filter-choices-by-keyword]]
            [re-com.tour     :refer [make-tour start-tour make-tour-nav]]
            [re-com.popover  :refer [popover make-button make-link]]
            [reagent.core    :as    reagent]))


(def demos [{:id 1 :label "Basic example"}
            {:id 2 :label "Other variations"}])


(defn demo1
  []
  (let [demo-tour (make-tour [:step1 :step2 :step3 :step4])]
    (fn []
      [:div
       [:p "The four buttons below are all part of this tour. Click on the first button to start the tour, then use the navigation buttons to move through the tour."]
       [:p "Any individual component can be the included in the tour, as long as you wrap it in a popover and conform the instrucitons on the left."]
       [h-box
        :children [[popover
                    :position :above-center
                    :showing? (:step1 demo-tour)
                    :anchor [button
                             :label "Start Tour!"
                             :on-click #(start-tour demo-tour)
                             :style {:font-weight "bold" :color "yellow"}
                             :class "btn-info"]
                    :popover {:title         [:strong "Tour 1 of 4"]
                              :close-button? true
                              :body          [:div "So this is the first tour popover"
                                              [make-tour-nav demo-tour]]}]
                   [popover
                    :position :above-center
                    :showing? (:step2 demo-tour)
                    :anchor [make-button (:step2 demo-tour) "info" "Tour 2"]
                    :popover {:title         [:strong "Tour 2 of 4"]
                              :close-button? true
                              :body          [:div "And this is the second tour popover"
                                              [make-tour-nav demo-tour]]}]
                   [popover
                    :position :above-center
                    :showing? (:step3 demo-tour)
                    :anchor [make-button (:step3 demo-tour) "info" "Tour 3"]
                    :popover {:title         [:strong "Tour 3 of 4"]
                              :close-button? true
                              :body          [:div "Penultimate tour popover"
                                              [make-tour-nav demo-tour]]}]
                   [popover
                    :position :above-right
                    :showing? (:step4 demo-tour)
                    :anchor [make-button (:step4 demo-tour) "info" "Tour 4"]
                    :popover {:title         [:strong "Tour 4 of 4"]
                              :close-button? true
                              :body          [:div "Lucky last tour popover"
                                              [make-tour-nav demo-tour]]}]]]
       ])))


(defn demo2
  []
  [:span "*** TODO ***"])


(defn notes
  []
  [v-box
   :width    "500px"
   :children [[:div.h4 "General notes"]
              [:ul
               [:li "To create a tour component, do the following:"
                [:ul
                 [:li.spacer "Make a tour object, declaring all the steps in your tour."]
                 [:li.spacer "For example: " [:br] [:code "(let [demo-tour (make-tour [:step1 :step2 :step3])])"]]
                 [:li.spacer "Wrap all the components in your tour with a popover component."]
                 [:li.spacer "The popover " [:code ":showing?"] " parameter should look like this: " [:br] [:code ":showing? (:step1 demo-tour)"]]
                 [:li.spacer "To add navigation buttons to the popover, add the following component to the end of your popover's " [:code ":body"] " markup: " [:br] [:code "[make-tour-nav demo-tour]"]]
                 [:li.spacer "To start the tour, call: " [:code "(start-tour demo-tour)"]]
                 [:li.spacer "To finish the tour, call: " [:code "(finish-tour demo-tour)"]]]]]]])


(defn panel
  []
  (let [selected-demo-id (reagent/atom 1)]
    (fn []
      [v-box
       :children [[:h3.page-header "Tour"]
                  [h-box
                   :gap      "50px"
                   :children [[notes]
                              [v-box
                               :gap       "15px"
                               :size      "auto"
                               :min-width "500px"
                               :children  [[h-box
                                            :gap      "10px"
                                            :align    :center
                                            :children [[label :label "Select a demo"]
                                                       [single-dropdown
                                                        :choices   demos
                                                        :model     selected-demo-id
                                                        :width     "300px"
                                                        :on-change #(reset! selected-demo-id %)]]]
                                           [gap :size "0px"] ;; Force a bit more space here
                                           (case @selected-demo-id
                                             1 [demo1]
                                             2 [demo2])]]]]]])))