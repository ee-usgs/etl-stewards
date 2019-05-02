  from ars_result
      join activity_swap_stewards
        on ars_result.activity_identifier = activity_swap_stewards.activity
      left join char_name_to_type
        on ars_result.characteristic_name = char_name_to_type.characteristic_name
