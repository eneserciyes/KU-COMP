
-- VHDL Instantiation Created from source file counter.vhd -- 01:29:07 12/13/2019
--
-- Notes: 
-- 1) This instantiation template has been automatically generated using types
-- std_logic and std_logic_vector for the ports of the instantiated module
-- 2) To use this template to instantiate this entity, cut-and-paste and then edit

	COMPONENT counter
	PORT(
		clk : IN std_logic;
		dec : IN std_logic;
		counter_init : IN std_logic_vector(2 downto 0);          
		zero : OUT std_logic
		);
	END COMPONENT;

	Inst_counter: counter PORT MAP(
		clk => ,
		dec => ,
		counter_init => ,
		zero => 
	);


